package com.hashalbum.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity representing image metadata stored in the database.
 * The hash is used as the primary key to uniquely identify images
 * even if they are moved to different locations.
 */
@Entity(tableName = "image_data")
data class ImageData(
    @PrimaryKey
    val hash: String,           // SHA-256 hash of the image content
    val remark: String = "",    // User's remark/note for this image
    val lastKnownPath: String = "", // Last known file path (for reference)
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val mediaType: String = "image"
)
