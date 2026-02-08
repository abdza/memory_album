package com.hashalbum.app.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hashalbum.app.HashAlbumApp
import com.hashalbum.app.R
import com.hashalbum.app.data.GalleryImage
import com.hashalbum.app.data.GalleryItem
import com.hashalbum.app.data.ImageData
import com.hashalbum.app.data.ImageRepository
import com.hashalbum.app.data.PathInfo
import com.hashalbum.app.data.SearchResultItem
import com.hashalbum.app.util.ImageBucket
import com.hashalbum.app.util.ImageHasher
import com.hashalbum.app.util.MediaStoreHelper
import com.hashalbum.app.util.PathValidator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ImageRepository

    private val _images = MutableStateFlow<List<GalleryImage>>(emptyList())
    val images: StateFlow<List<GalleryImage>> = _images.asStateFlow()

    private val _galleryItems = MutableStateFlow<List<GalleryItem>>(emptyList())
    val galleryItems: StateFlow<List<GalleryItem>> = _galleryItems.asStateFlow()

    private val _buckets = MutableStateFlow<List<ImageBucket>>(emptyList())
    val buckets: StateFlow<List<ImageBucket>> = _buckets.asStateFlow()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _currentBucket = MutableLiveData<ImageBucket?>(null)
    val currentBucket: LiveData<ImageBucket?> = _currentBucket

    private val _searchResults = MutableStateFlow<List<SearchResultItem>>(emptyList())
    val searchResults: StateFlow<List<SearchResultItem>> = _searchResults.asStateFlow()

    private val _isSearchMode = MutableStateFlow(false)
    val isSearchMode: StateFlow<Boolean> = _isSearchMode.asStateFlow()

    private val hashCache = mutableMapOf<Uri, String>()

    init {
        val database = (application as HashAlbumApp).database
        repository = ImageRepository(database.imageDataDao(), database.imagePathDao(), database.imageTagDao(), database.contactDao())
    }

    fun loadAllImages() {
        viewModelScope.launch {
            _isLoading.value = true
            _currentBucket.value = null

            val loadedImages = MediaStoreHelper.loadAllImages(getApplication())
            _images.value = loadedImages
            _galleryItems.value = groupImagesByDate(loadedImages)

            _isLoading.value = false

            validateAllPaths()
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
            _galleryItems.value = groupImagesByDate(loadedImages)

            _isLoading.value = false
        }
    }

    suspend fun getImageHash(uri: Uri): String? {
        return hashCache[uri] ?: run {
            val hash = ImageHasher.generateHash(getApplication(), uri)
            hash?.let { hashCache[uri] = it }
            hash
        }
    }

    suspend fun getImageData(hash: String): ImageData? {
        return repository.getByHash(hash)
    }

    suspend fun saveRemark(uri: Uri, remark: String) {
        val hash = getImageHash(uri) ?: return
        repository.saveOrUpdateRemark(hash, remark, uri.toString())
    }

    suspend fun hasRemark(uri: Uri): Boolean {
        val hash = getImageHash(uri) ?: return false
        val imageData = repository.getByHash(hash)
        return !imageData?.remark.isNullOrBlank()
    }

    suspend fun getRemark(uri: Uri): String {
        val hash = getImageHash(uri) ?: return ""
        return repository.getByHash(hash)?.remark ?: ""
    }

    suspend fun trackImagePath(uri: Uri) {
        val hash = getImageHash(uri) ?: return
        repository.addOrUpdatePath(hash, uri.toString())
    }

    suspend fun getTagsForImage(uri: Uri): List<String> {
        val hash = getImageHash(uri) ?: return emptyList()
        return repository.getTagsForHashSync(hash)
    }

    suspend fun addTagsToImage(uri: Uri, tags: List<String>) {
        val hash = getImageHash(uri) ?: return
        // Ensure ImageData row exists
        val existing = repository.getByHash(hash)
        if (existing == null) {
            repository.insert(ImageData(hash = hash, lastKnownPath = uri.toString()))
        }
        // Ensure path is tracked so search results can show thumbnail
        repository.addOrUpdatePath(hash, uri.toString())
        repository.addTagsToImage(hash, tags)
    }

    suspend fun removeTagFromImage(uri: Uri, tag: String) {
        val hash = getImageHash(uri) ?: return
        repository.removeTagFromImage(hash, tag)
    }

    fun addTagsToImages(uris: List<Uri>, tags: List<String>) {
        viewModelScope.launch {
            for (uri in uris) {
                addTagsToImage(uri, tags)
            }
        }
    }

    fun saveRemarkToImages(uris: List<Uri>, remark: String) {
        viewModelScope.launch {
            for (uri in uris) {
                saveRemark(uri, remark)
            }
        }
    }

    fun searchRemarks(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }
        _isSearchMode.value = true
        viewModelScope.launch {
            repository.searchByRemark(query).collectLatest { imageDataList ->
                // Also search by tag
                val tagQuery = query.trimStart('#')
                val tagMatchedHashes = repository.searchByTag(tagQuery)
                val remarkHashes = imageDataList.map { it.hash }.toSet()

                // Load ImageData for tag-only matches
                val tagOnlyData = tagMatchedHashes
                    .filter { it !in remarkHashes }
                    .mapNotNull { repository.getByHash(it) }

                val allImageData = imageDataList + tagOnlyData

                val results = allImageData.map { imageData ->
                    val paths = repository.getPathsForHashSync(imageData.hash).map { imagePath ->
                        PathInfo(
                            uri = Uri.parse(imagePath.path),
                            lastSeen = imagePath.lastSeen,
                            isValid = imagePath.isValid
                        )
                    }
                    val tags = repository.getTagsForHashSync(imageData.hash)
                    SearchResultItem(imageData = imageData, paths = paths, tags = tags)
                }
                _searchResults.value = results

                validateSearchResultPaths(results)
            }
        }
    }

    private fun validateSearchResultPaths(results: List<SearchResultItem>) {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()
            var changed = false
            for (result in results) {
                for (pathInfo in result.paths) {
                    val valid = PathValidator.isPathValid(context, pathInfo.uri)
                    if (valid != pathInfo.isValid) {
                        repository.updatePathValidity(result.imageData.hash, pathInfo.uri.toString(), valid)
                        changed = true
                    }
                }
            }
            if (changed) {
                withContext(Dispatchers.Main) {
                    val updated = _searchResults.value.map { item ->
                        val freshPaths = repository.getPathsForHashSync(item.imageData.hash).map { imagePath ->
                            PathInfo(
                                uri = Uri.parse(imagePath.path),
                                lastSeen = imagePath.lastSeen,
                                isValid = imagePath.isValid
                            )
                        }
                        item.copy(paths = freshPaths)
                    }
                    _searchResults.value = updated
                }
            }
        }
    }

    fun validateAllPaths() {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()
            val allPaths = repository.getAllPathsSync()
            for (imagePath in allPaths) {
                try {
                    val uri = Uri.parse(imagePath.path)
                    val valid = PathValidator.isPathValid(context, uri)
                    if (valid != imagePath.isValid) {
                        repository.updatePathValidity(imagePath.hash, imagePath.path, valid)
                    }
                } catch (_: Exception) {
                    repository.updatePathValidity(imagePath.hash, imagePath.path, false)
                }
            }
            repository.deleteStaleInvalidPaths(30)
        }
    }

    private fun groupImagesByDate(images: List<GalleryImage>): List<GalleryItem> {
        if (images.isEmpty()) return emptyList()

        val app = getApplication<Application>()
        val todayLabel = app.getString(R.string.today)
        val yesterdayLabel = app.getString(R.string.yesterday)

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
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())

        val result = mutableListOf<GalleryItem>()
        var currentLabel: String? = null

        for (image in images) {
            val imageDate = Calendar.getInstance().apply {
                timeInMillis = image.dateModified * 1000
            }

            val label = when {
                imageDate.timeInMillis >= today.timeInMillis -> todayLabel
                imageDate.timeInMillis >= yesterday.timeInMillis -> yesterdayLabel
                else -> dateFormat.format(Date(image.dateModified * 1000))
            }

            if (label != currentLabel) {
                result.add(GalleryItem.DateHeader(label))
                currentLabel = label
            }
            result.add(GalleryItem.ImageItem(image))
        }

        return result
    }

    fun clearSearch() {
        _isSearchMode.value = false
        _searchResults.value = emptyList()
    }

    suspend fun getContactsForImage(uri: Uri): List<com.hashalbum.app.data.Contact> {
        val hash = getImageHash(uri) ?: return emptyList()
        return repository.getContactsForImage(hash)
    }

    suspend fun addContactsToImage(uri: Uri, names: List<String>) {
        val hash = getImageHash(uri) ?: return
        val existing = repository.getByHash(hash)
        if (existing == null) {
            repository.insert(ImageData(hash = hash, lastKnownPath = uri.toString()))
        }
        repository.addOrUpdatePath(hash, uri.toString())
        repository.addContactsToImage(hash, names)
    }

    suspend fun removeContactFromImage(uri: Uri, contactId: Long) {
        val hash = getImageHash(uri) ?: return
        repository.removeContactFromImage(hash, contactId)
    }

    fun addContactsToImages(uris: List<Uri>, names: List<String>) {
        viewModelScope.launch {
            for (uri in uris) {
                addContactsToImage(uri, names)
            }
        }
    }
}
