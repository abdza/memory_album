package com.hashalbum.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDataDao {
    
    @Query("SELECT * FROM image_data WHERE hash = :hash")
    suspend fun getByHash(hash: String): ImageData?
    
    @Query("SELECT * FROM image_data WHERE hash = :hash")
    fun getByHashFlow(hash: String): Flow<ImageData?>
    
    @Query("SELECT * FROM image_data ORDER BY updatedAt DESC")
    fun getAllImages(): Flow<List<ImageData>>
    
    @Query("SELECT * FROM image_data WHERE remark != '' ORDER BY updatedAt DESC")
    fun getImagesWithRemarks(): Flow<List<ImageData>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imageData: ImageData)
    
    @Update
    suspend fun update(imageData: ImageData)
    
    @Query("UPDATE image_data SET remark = :remark, updatedAt = :updatedAt WHERE hash = :hash")
    suspend fun updateRemark(hash: String, remark: String, updatedAt: Long = System.currentTimeMillis())
    
    @Query("UPDATE image_data SET lastKnownPath = :path, updatedAt = :updatedAt WHERE hash = :hash")
    suspend fun updatePath(hash: String, path: String, updatedAt: Long = System.currentTimeMillis())
    
    @Delete
    suspend fun delete(imageData: ImageData)
    
    @Query("DELETE FROM image_data WHERE hash = :hash")
    suspend fun deleteByHash(hash: String)
}
