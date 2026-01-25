package com.hashalbum.app.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hashalbum.app.HashAlbumApp
import com.hashalbum.app.data.GalleryImage
import com.hashalbum.app.data.ImageData
import com.hashalbum.app.data.ImageRepository
import com.hashalbum.app.util.ImageBucket
import com.hashalbum.app.util.ImageHasher
import com.hashalbum.app.util.MediaStoreHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ImageRepository
    
    private val _images = MutableStateFlow<List<GalleryImage>>(emptyList())
    val images: StateFlow<List<GalleryImage>> = _images.asStateFlow()
    
    private val _buckets = MutableStateFlow<List<ImageBucket>>(emptyList())
    val buckets: StateFlow<List<ImageBucket>> = _buckets.asStateFlow()
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _currentBucket = MutableLiveData<ImageBucket?>(null)
    val currentBucket: LiveData<ImageBucket?> = _currentBucket
    
    // Cache for image hashes to avoid recalculating
    private val hashCache = mutableMapOf<Uri, String>()
    
    init {
        val database = (application as HashAlbumApp).database
        repository = ImageRepository(database.imageDataDao())
    }
    
    fun loadAllImages() {
        viewModelScope.launch {
            _isLoading.value = true
            _currentBucket.value = null
            
            val loadedImages = MediaStoreHelper.loadAllImages(getApplication())
            _images.value = loadedImages
            
            _isLoading.value = false
        }
    }
    
    fun loadBuckets() {
        viewModelScope.launch {
            _isLoading.value = true
            
            val loadedBuckets = MediaStoreHelper.getImageBuckets(getApplication())
            _buckets.value = loadedBuckets
            
            _isLoading.value = false
        }
    }
    
    fun loadImagesFromBucket(bucket: ImageBucket) {
        viewModelScope.launch {
            _isLoading.value = true
            _currentBucket.value = bucket
            
            val loadedImages = MediaStoreHelper.loadImagesFromBucket(getApplication(), bucket.id)
            _images.value = loadedImages
            
            _isLoading.value = false
        }
    }
    
    /**
     * Get the hash for an image, using cache if available.
     */
    suspend fun getImageHash(uri: Uri): String? {
        return hashCache[uri] ?: run {
            val hash = ImageHasher.generateHash(getApplication(), uri)
            hash?.let { hashCache[uri] = it }
            hash
        }
    }
    
    /**
     * Get image data by hash from database.
     */
    suspend fun getImageData(hash: String): ImageData? {
        return repository.getByHash(hash)
    }
    
    /**
     * Save or update remark for an image.
     */
    suspend fun saveRemark(uri: Uri, remark: String) {
        val hash = getImageHash(uri) ?: return
        repository.saveOrUpdateRemark(hash, remark, uri.toString())
    }
    
    /**
     * Check if an image has a remark saved.
     */
    suspend fun hasRemark(uri: Uri): Boolean {
        val hash = getImageHash(uri) ?: return false
        val imageData = repository.getByHash(hash)
        return !imageData?.remark.isNullOrBlank()
    }
    
    /**
     * Get remark for an image.
     */
    suspend fun getRemark(uri: Uri): String {
        val hash = getImageHash(uri) ?: return ""
        return repository.getByHash(hash)?.remark ?: ""
    }
}
