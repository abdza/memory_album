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
import com.hashalbum.app.R
import com.hashalbum.app.databinding.ActivityMainBinding
import com.hashalbum.app.util.ImageBucket
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var bucketAdapter: BucketAdapter

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
        galleryAdapter = GalleryAdapter { image, position ->
            // Open image viewer
            val imageList = if (viewModel.isSearchMode.value) {
                viewModel.searchResults.value
            } else {
                viewModel.images.value
            }
            val intent = Intent(this, ImageViewerActivity::class.java).apply {
                putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, position)
                putStringArrayListExtra(
                    ImageViewerActivity.EXTRA_IMAGE_URIS,
                    ArrayList(imageList.map { it.uri.toString() })
                )
            }
            startActivity(intent)
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
                    galleryAdapter.submitList(results)
                    binding.emptyView.visibility = if (results.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isSearchMode.collectLatest { isSearchMode ->
                if (isSearchMode) {
                    galleryAdapter.submitList(viewModel.searchResults.value)
                    binding.emptyView.visibility = if (viewModel.searchResults.value.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    galleryAdapter.submitList(viewModel.images.value)
                    binding.emptyView.visibility = if (viewModel.images.value.isEmpty()) View.VISIBLE else View.GONE
                    // Restore title
                    val currentBucket = viewModel.currentBucket.value
                    supportActionBar?.title = currentBucket?.name ?: getString(R.string.all_images)
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else if (viewModel.isSearchMode.value) {
            // Exit search mode
            viewModel.clearSearch()
            invalidateOptionsMenu()
        } else if (viewModel.currentBucket.value != null) {
            // Go back to all images
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
