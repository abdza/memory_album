package com.hashalbum.app.data

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "image_tags",
    primaryKeys = ["hash", "tag"],
    foreignKeys = [
        ForeignKey(
            entity = ImageData::class,
            parentColumns = ["hash"],
            childColumns = ["hash"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ImageTag(
    val hash: String,
    val tag: String,
    val createdAt: Long = System.currentTimeMillis()
)
