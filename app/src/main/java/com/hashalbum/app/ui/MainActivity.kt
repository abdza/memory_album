package com.hashalbum.app.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
            val intent = Intent(this, ImageViewerActivity::class.java).apply {
                putExtra(ImageViewerActivity.EXTRA_IMAGE_POSITION, position)
                putStringArrayListExtra(
                    ImageViewerActivity.EXTRA_IMAGE_URIS,
                    ArrayList(viewModel.images.value.map { it.uri.toString() })
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

    private fun loadBuckets() {
        viewModel.loadBuckets()
    }
    
    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.images.collectLatest { images ->
                galleryAdapter.submitList(images)
                binding.emptyView.visibility = if (images.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.buckets.collectLatest { buckets ->
                bucketAdapter.submitList(buckets)
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
