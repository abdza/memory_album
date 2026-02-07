package com.hashalbum.app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hashalbum.app.R
import com.hashalbum.app.databinding.ActivityMainBinding
import com.hashalbum.app.util.ImageBucket
import com.hashalbum.app.util.TagParser
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var bucketAdapter: BucketAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            loadImages()
            loadBuckets()
        } else {
            Toast.makeText(
                this,
                "Permission required to access photos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupBucketDrawer()
        setupSwipeRefresh()
        observeViewModel()

        setupSelectionBar()
        checkPermissionAndLoad()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.all_images)

        binding.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setupRecyclerView() {
        galleryAdapter = GalleryAdapter(
            onImageClick = { image, position ->
                val imageList = viewModel.images.value
                val intent = Intent(this, ImageViewerActivity::class.java).apply {
                    putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, position)
                    putStringArrayListExtra(
                        ImageViewerActivity.EXTRA_IMAGE_URIS,
                        ArrayList(imageList.map { it.uri.toString() })
                    )
                }
                startActivity(intent)
            },
            onLongPress = { image, _ ->
                enterSelectionMode()
                galleryAdapter.toggleSelection(image.uri)
            },
            onSelectionChanged = { count ->
                updateSelectionCount(count)
            },
            onDoubleTap = { image, holder ->
                lifecycleScope.launch {
                    val remark = viewModel.getRemark(image.uri)
                    val tags = viewModel.getTagsForImage(image.uri)
                    holder.showInfoOverlay(remark, tags)
                }
            }
        )

        searchResultAdapter = SearchResultAdapter { searchResult ->
            val validPath = searchResult.paths.firstOrNull { it.isValid }
                ?: searchResult.paths.firstOrNull()
            if (validPath != null) {
                val intent = Intent(this, ImageViewerActivity::class.java).apply {
                    putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, 0)
                    putStringArrayListExtra(
                        ImageViewerActivity.EXTRA_IMAGE_URIS,
                        arrayListOf(validPath.uri.toString())
                    )
                }
                startActivity(intent)
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = galleryAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupBucketDrawer() {
        bucketAdapter = BucketAdapter { bucket ->
            if (bucket != null) {
                viewModel.loadImagesFromBucket(bucket)
                supportActionBar?.title = bucket.name
            } else {
                viewModel.loadAllImages()
                supportActionBar?.title = getString(R.string.all_images)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.bucketRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bucketAdapter
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            val currentBucket = viewModel.currentBucket.value
            if (currentBucket != null) {
                viewModel.loadImagesFromBucket(currentBucket)
            } else {
                loadImages()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_remarks)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchRemarks(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchRemarks(it) }
                return true
            }
        })

        searchItem.setOnActionExpandListener(object : android.view.MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: android.view.MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: android.view.MenuItem): Boolean {
                viewModel.clearSearch()
                return true
            }
        })

        return true
    }

    private fun loadBuckets() {
        viewModel.loadBuckets()
    }

    private fun switchToSearchMode() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = searchResultAdapter
        supportActionBar?.title = getString(R.string.search_results)
    }

    private fun switchToGalleryMode() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = galleryAdapter
        val currentBucket = viewModel.currentBucket.value
        supportActionBar?.title = currentBucket?.name ?: getString(R.string.all_images)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.images.collectLatest { images ->
                if (!viewModel.isSearchMode.value) {
                    galleryAdapter.submitList(images)
                    binding.emptyView.visibility = if (images.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.buckets.collectLatest { buckets ->
                bucketAdapter.submitList(buckets)
            }
        }

        lifecycleScope.launch {
            viewModel.searchResults.collectLatest { results ->
                if (viewModel.isSearchMode.value) {
                    searchResultAdapter.submitList(results)
                    binding.emptyView.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isSearchMode.collectLatest { isSearchMode ->
                if (isSearchMode) {
                    switchToSearchMode()
                    searchResultAdapter.submitList(viewModel.searchResults.value)
                    binding.emptyView.visibility = if (viewModel.searchResults.value.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    switchToGalleryMode()
                    galleryAdapter.submitList(viewModel.images.value)
                    binding.emptyView.visibility = if (viewModel.images.value.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading && galleryAdapter.itemCount == 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun checkPermissionAndLoad() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                loadImages()
                loadBuckets()
            }
            shouldShowRequestPermissionRationale(permission) -> {
                Toast.makeText(
                    this,
                    "Permission needed to show your photos",
                    Toast.LENGTH_LONG
                ).show()
                permissionLauncher.launch(permission)
            }
            else -> {
                permissionLauncher.launch(permission)
            }
        }
    }

    private fun setupSelectionBar() {
        binding.cancelSelectionButton.setOnClickListener {
            exitSelectionMode()
        }
        binding.batchTagButton.setOnClickListener {
            showBatchTagDialog()
        }
        binding.batchRemarkButton.setOnClickListener {
            showBatchRemarkDialog()
        }
    }

    private fun enterSelectionMode() {
        galleryAdapter.enterSelectionMode()
        binding.selectionBar.visibility = View.VISIBLE
        binding.swipeRefresh.isEnabled = false
        updateSelectionCount(galleryAdapter.getSelectedCount())
    }

    private fun exitSelectionMode() {
        galleryAdapter.exitSelectionMode()
        binding.selectionBar.visibility = View.GONE
        binding.swipeRefresh.isEnabled = true
    }

    private fun updateSelectionCount(count: Int) {
        binding.selectionCount.text = "$count selected"
    }

    private fun showBatchTagDialog() {
        val selectedImages = galleryAdapter.getSelectedImages()
        if (selectedImages.isEmpty()) return

        val input = android.widget.EditText(this).apply {
            hint = getString(R.string.enter_tags)
            setPadding(48, 32, 48, 32)
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.add_tags)
            .setView(input)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val tags = TagParser.parse(input.text.toString())
                if (tags.isNotEmpty()) {
                    val uris = selectedImages.map { it.uri }
                    viewModel.addTagsToImages(uris, tags)
                    Toast.makeText(this, R.string.tags_added, Toast.LENGTH_SHORT).show()
                    exitSelectionMode()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showBatchRemarkDialog() {
        val selectedImages = galleryAdapter.getSelectedImages()
        if (selectedImages.isEmpty()) return

        val input = android.widget.EditText(this).apply {
            hint = getString(R.string.enter_remark)
            setPadding(48, 32, 48, 32)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
            minLines = 3
        }
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.set_remark)
            .setView(input)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val remark = input.text.toString().trim()
                if (remark.isNotEmpty()) {
                    val uris = selectedImages.map { it.uri }
                    viewModel.saveRemarkToImages(uris, remark)
                    Toast.makeText(this, R.string.remark_set, Toast.LENGTH_SHORT).show()
                    exitSelectionMode()
                }
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (galleryAdapter.isSelectionMode) {
            exitSelectionMode()
        } else if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (viewModel.isSearchMode.value) {
            viewModel.clearSearch()
            invalidateOptionsMenu()
        } else if (viewModel.currentBucket.value != null) {
            viewModel.loadAllImages()
            supportActionBar?.title = getString(R.string.all_images)
        } else {
            super.onBackPressed()
        }
    }

    private fun loadImages() {
        viewModel.loadAllImages()
    }
}
