package com.hashalbum.app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "image_contacts",
    primaryKeys = ["hash", "contactId"],
    foreignKeys = [
        ForeignKey(
            entity = ImageData::class,
            parentColumns = ["hash"],
            childColumns = ["hash"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Contact::class,
            parentColumns = ["id"],
            childColumns = ["contactId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["contactId"])]
)
data class ImageContact(
    val hash: String,
    val contactId: Long,
    val createdAt: Long = System.currentTimeMillis()
)

data class ContactWithCount(
    val id: Long,
    val name: String,
    val imageCount: Int
)
