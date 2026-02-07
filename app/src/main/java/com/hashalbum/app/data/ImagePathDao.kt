package com.hashalbum.app.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagePathDao {

    @Query("SELECT * FROM image_paths WHERE hash = :hash")
    fun getPathsForHash(hash: String): Flow<List<ImagePath>>

    @Query("SELECT * FROM image_paths WHERE hash = :hash")
    suspend fun getPathsForHashSync(hash: String): List<ImagePath>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPath(imagePath: ImagePath)

    @Query("UPDATE image_paths SET lastSeen = :lastSeen WHERE hash = :hash AND path = :path")
    suspend fun updateLastSeen(hash: String, path: String, lastSeen: Long = System.currentTimeMillis())

    @Query("UPDATE image_paths SET isValid = :isValid WHERE hash = :hash AND path = :path")
    suspend fun updateValidity(hash: String, path: String, isValid: Boolean)

    @Query("DELETE FROM image_paths WHERE hash = :hash AND path = :path")
    suspend fun deletePath(hash: String, path: String)

    @Query("DELETE FROM image_paths WHERE isValid = 0 AND lastSeen < :cutoffTime")
    suspend fun deleteStaleInvalidPaths(cutoffTime: Long)

    @Query("SELECT * FROM image_paths")
    fun getAllPaths(): Flow<List<ImagePath>>

    @Query("SELECT * FROM image_paths")
    suspend fun getAllPathsSync(): List<ImagePath>
}
