package com.hashalbum.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageTagDao {

    @Query("SELECT * FROM image_tags WHERE hash = :hash")
    suspend fun getTagsForHashSync(hash: String): List<ImageTag>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(tags: List<ImageTag>)

    @Query("DELETE FROM image_tags WHERE hash = :hash AND tag = :tag")
    suspend fun deleteTag(hash: String, tag: String)

    @Query("SELECT DISTINCT hash FROM image_tags WHERE tag LIKE '%' || :query || '%'")
    suspend fun searchByTag(query: String): List<String>
}
