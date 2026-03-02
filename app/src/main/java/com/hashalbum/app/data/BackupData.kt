package com.hashalbum.app.data

data class BackupImageData(
    val hash: String,
    val remark: String,
    val mediaType: String,
    val createdAt: Long,
    val updatedAt: Long,
    val tags: List<String>,
    val contactIds: List<Long>
)

data class BackupContact(
    val id: Long,
    val name: String,
    val createdAt: Long
)

data class BackupData(
    val version: Int,
    val exportDate: String,
    val exportDateMs: Long,
    val images: List<BackupImageData>,
    val contacts: List<BackupContact>
)

data class ImportResult(
    val imagesRestored: Int,
    val contactsRestored: Int,
    val skipped: Int
)
