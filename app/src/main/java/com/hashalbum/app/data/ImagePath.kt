package com.hashalbum.app.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "image_paths",
    primaryKeys = ["hash", "path"],
    foreignKeys = [
        ForeignKey(
            entity = ImageData::class,
            parentColumns = ["hash"],
            childColumns = ["hash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImagePath(
    val hash: String,
    val path: String,
    val lastSeen: Long = System.currentTimeMillis(),
    val isValid: Boolean = true
)
