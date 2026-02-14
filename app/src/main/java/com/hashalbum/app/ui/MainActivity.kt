package com.hashalbum.app.ui

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
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
import com.hashalbum.app.data.GalleryItem
import com.hashalbum.app.data.MediaType
import com.hashalbum.app.databinding.ActivityMainBinding
import com.hashalbum.app.util.ImageBucket
import com.hashalbum.app.util.PhoneContactsHelper
import com.hashalbum.app.util.TagParser
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GalleryViewModel by viewModels()
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var bucketAdapter: BucketAdapter
    private lateinit var searchResultAdapter: SearchResultAdapter

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            loadImages()
            loadBuckets()
        } else {
            Toast.makeText(
                this,
                "Permission required to access photos and videos",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val contactsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showBatchContactDialog()
        } else {
            Toast.makeText(this, R.string.contacts_permission_needed, Toast.LENGTH_SHORT).show()
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
        setupFab()
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
            onImageClick = { image, _ ->
                val imageItems = viewModel.galleryItems.value
                    .filterIsInstance<GalleryItem.ImageItem>()
                val imageUris = ArrayList(imageItems.map { it.image.uri.toString() })
                val mediaTypes = ArrayList(imageItems.map {
                    if (it.image.mediaType == MediaType.VIDEO) "video" else "image"
                })
                val durations = ArrayList(imageItems.map { it.image.duration.toString() })
                val imagePosition = imageItems.indexOfFirst { it.image.uri == image.uri }
                val intent = Intent(this, ImageViewerActivity::class.java).apply {
                    putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, imagePosition.coerceAtLeast(0))
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_IMAGE_URIS, imageUris)
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_MEDIA_TYPES, mediaTypes)
                    putStringArrayListExtra(ImageViewerActivity.EXTRA_DURATIONS, durations)
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
                    val contacts = viewModel.getContactsForImage(image.uri)
                    holder.showInfoOverlay(remark, tags, contacts.map { it.name })
                }
            }
        )

        searchResultAdapter = SearchResultAdapter { searchResult ->
            val validPath = searchResult.paths.firstOrNull { it.isValid }
                ?: searchResult.paths.firstOrNull()
            if (validPath != null) {
                val mediaType = searchResult.imageData.mediaType
                val intent = Intent(this, ImageViewerActivity::class.java).apply {
                    putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, 0)
                    putStringArrayListExtra(
                        ImageViewerActivity.EXTRA_IMAGE_URIS,
                        arrayListOf(validPath.uri.toString())
                    )
                    putStringArrayListExtra(
                        ImageViewerActivity.EXTRA_MEDIA_TYPES,
                        arrayListOf(mediaType)
                    )
                    putStringArrayListExtra(
                        ImageViewerActivity.EXTRA_DURATIONS,
                        arrayListOf("0")
                    )
                }
                startActivity(intent)
            }
        }

        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (galleryAdapter.getItemViewType(position) == 0) 3 else 1
            }
        }

        binding.recyclerView.apply {
            layoutManager = gridLayoutManager
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

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_contacts -> {
                startActivity(Intent(this, ContactsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (galleryAdapter.getItemViewType(position) == 0) 3 else 1
            }
        }
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = galleryAdapter
        val currentBucket = viewModel.currentBucket.value
        supportActionBar?.title = currentBucket?.name ?: getString(R.string.all_images)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.galleryItems.collectLatest { items ->
                if (!viewModel.isSearchMode.value) {
                    galleryAdapter.submitList(items)
                    binding.emptyView.visibility = if (items.isEmpty()) View.VISIBLE else View.GONE
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
                    binding.fabJumpToDate.hide()
                    searchResultAdapter.submitList(viewModel.searchResults.value)
                    binding.emptyView.visibility = if (viewModel.searchResults.value.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    switchToGalleryMode()
                    binding.fabJumpToDate.show()
                    galleryAdapter.submitList(viewModel.galleryItems.value)
                    binding.emptyView.visibility = if (viewModel.galleryItems.value.isEmpty()) View.VISIBLE else View.GONE
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
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        when {
            allGranted -> {
                loadImages()
                loadBuckets()
            }
            permissions.any { shouldShowRequestPermissionRationale(it) } -> {
                Toast.makeText(
                    this,
                    "Permission needed to show your photos and videos",
                    Toast.LENGTH_LONG
                ).show()
                permissionLauncher.launch(permissions)
            }
            else -> {
                permissionLauncher.launch(permissions)
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
        binding.batchContactButton.setOnClickListener {
            showBatchContactDialog()
        }
    }

    private fun enterSelectionMode() {
        galleryAdapter.enterSelectionMode()
        binding.selectionBar.visibility = View.VISIBLE
        binding.fabJumpToDate.hide()
        binding.swipeRefresh.isEnabled = false
        updateSelectionCount(galleryAdapter.getSelectedCount())
    }

    private fun exitSelectionMode() {
        galleryAdapter.exitSelectionMode()
        binding.selectionBar.visibility = View.GONE
        if (!viewModel.isSearchMode.value) {
            binding.fabJumpToDate.show()
        }
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

    private fun showBatchContactDialog() {
        val selectedImages = galleryAdapter.getSelectedImages()
        if (selectedImages.isEmpty()) return

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            contactsPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            return
        }

        val phoneContacts = PhoneContactsHelper.getPhoneContacts(this)
        if (phoneContacts.isEmpty()) {
            Toast.makeText(this, R.string.no_phone_contacts, Toast.LENGTH_SHORT).show()
            return
        }

        val selectedNames = mutableSetOf<String>()

        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(48, 24, 48, 0)
        }

        val searchInput = EditText(this).apply {
            hint = getString(R.string.search_contacts)
            setSingleLine()
        }
        layout.addView(searchInput)

        val listView = ListView(this).apply {
            choiceMode = ListView.CHOICE_MODE_MULTIPLE
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, phoneContacts.toMutableList())
        listView.adapter = adapter
        layout.addView(listView, android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT, 800
        ))

        listView.setOnItemClickListener { _, _, position, _ ->
            val name = adapter.getItem(position) ?: return@setOnItemClickListener
            if (listView.isItemChecked(position)) {
                selectedNames.add(name)
            } else {
                selectedNames.remove(name)
            }
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                adapter.filter.filter(s) {
                    for (i in 0 until adapter.count) {
                        val name = adapter.getItem(i) ?: continue
                        listView.setItemChecked(i, name in selectedNames)
                    }
                }
            }
        })

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.add_contacts)
            .setView(layout)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                if (selectedNames.isNotEmpty()) {
                    val uris = selectedImages.map { it.uri }
                    viewModel.addContactsToImages(uris, selectedNames.toList())
                    Toast.makeText(this, R.string.contacts_added, Toast.LENGTH_SHORT).show()
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

    private fun setupFab() {
        binding.fabJumpToDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedCal = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    jumpToDate(selectedCal)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun jumpToDate(selectedDate: Calendar) {
        val items = viewModel.galleryItems.value
        if (items.isEmpty()) {
            Toast.makeText(this, "No photos available", Toast.LENGTH_SHORT).show()
            return
        }

        // Build the label for the selected date using the same logic as groupImagesByDate
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val yesterday = Calendar.getInstance().apply {
            timeInMillis = today.timeInMillis
            add(Calendar.DAY_OF_YEAR, -1)
        }

        val targetLabel = when {
            selectedDate.timeInMillis >= today.timeInMillis -> getString(R.string.today)
            selectedDate.timeInMillis >= yesterday.timeInMillis -> getString(R.string.yesterday)
            else -> SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault()).format(Date(selectedDate.timeInMillis))
        }

        // Try exact match first
        val exactPosition = items.indexOfFirst { it is GalleryItem.DateHeader && it.label == targetLabel }
        if (exactPosition >= 0) {
            scrollToPosition(exactPosition)
            return
        }

        // No exact match - find nearest date header that is <= selected date
        // Selected date as epoch seconds (to compare with dateModified)
        val selectedEpochSec = selectedDate.timeInMillis / 1000

        var bestPosition = -1
        var bestDiff = Long.MAX_VALUE

        for (i in items.indices) {
            val item = items[i]
            if (item is GalleryItem.DateHeader) {
                // Find the first image after this header to get its date
                val nextImage = items.getOrNull(i + 1)
                if (nextImage is GalleryItem.ImageItem) {
                    val imageDateSec = nextImage.image.dateModified
                    // We want the nearest header where the images are <= selected date
                    if (imageDateSec <= selectedEpochSec) {
                        val diff = selectedEpochSec - imageDateSec
                        if (diff < bestDiff) {
                            bestDiff = diff
                            bestPosition = i
                        }
                    }
                }
            }
        }

        if (bestPosition >= 0) {
            scrollToPosition(bestPosition)
        } else {
            Toast.makeText(this, "No photos at or before the selected date", Toast.LENGTH_SHORT).show()
        }
    }

    private fun scrollToPosition(position: Int) {
        val layoutManager = binding.recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    private fun loadImages() {
        viewModel.loadAllImages()
    }
}
