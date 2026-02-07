package com.hashalbum.app.data

import kotlinx.coroutines.flow.Flow

class ImageRepository(private val imageDataDao: ImageDataDao) {
    
    val allImages: Flow<List<ImageData>> = imageDataDao.getAllImages()
    val imagesWithRemarks: Flow<List<ImageData>> = imageDataDao.getImagesWithRemarks()

    fun searchByRemark(query: String): Flow<List<ImageData>> = imageDataDao.searchByRemark(query)
    
    suspend fun getByHash(hash: String): ImageData? {
        return imageDataDao.getByHash(hash)
    }
    
    fun getByHashFlow(hash: String): Flow<ImageData?> {
        return imageDataDao.getByHashFlow(hash)
    }
    
    suspend fun insert(imageData: ImageData) {
        imageDataDao.insert(imageData)
    }
    
    suspend fun updateRemark(hash: String, remark: String) {
        imageDataDao.updateRemark(hash, remark)
    }
    
    suspend fun updatePath(hash: String, path: String) {
        imageDataDao.updatePath(hash, path)
    }
    
    suspend fun saveOrUpdateRemark(hash: String, remark: String, path: String) {
        val existing = imageDataDao.getByHash(hash)
        if (existing != null) {
            imageDataDao.updateRemark(hash, remark)
            if (existing.lastKnownPath != path) {
                imageDataDao.updatePath(hash, path)
            }
        } else {
            imageDataDao.insert(
                ImageData(
                    hash = hash,
                    remark = remark,
                    lastKnownPath = path
                )
            )
        }
    }
    
    suspend fun delete(hash: String) {
        imageDataDao.deleteByHash(hash)
    }
}
