package com.hashalbum.app.data

import com.hashalbum.app.util.BackupManager
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageRepository(
    private val imageDataDao: ImageDataDao,
    private val imagePathDao: ImagePathDao,
    private val imageTagDao: ImageTagDao,
    private val contactDao: ContactDao? = null
) {

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

    suspend fun saveOrUpdateRemark(hash: String, remark: String, path: String, mediaType: String = "image") {
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
                    lastKnownPath = path,
                    mediaType = mediaType
                )
            )
        }
        addOrUpdatePath(hash, path)
    }

    suspend fun addOrUpdatePath(hash: String, path: String, mediaType: String = "image") {
        // Ensure parent image_data row exists (FK constraint)
        val imageData = imageDataDao.getByHash(hash)
        if (imageData == null) {
            imageDataDao.insert(ImageData(hash = hash, lastKnownPath = path, mediaType = mediaType))
        }
        val existing = imagePathDao.getPathsForHashSync(hash).find { it.path == path }
        if (existing != null) {
            imagePathDao.updateLastSeen(hash, path)
        } else {
            imagePathDao.insertPath(ImagePath(hash = hash, path = path))
        }
    }

    suspend fun getPathsForHashSync(hash: String): List<ImagePath> {
        return imagePathDao.getPathsForHashSync(hash)
    }

    suspend fun updatePathValidity(hash: String, path: String, isValid: Boolean) {
        imagePathDao.updateValidity(hash, path, isValid)
    }

    suspend fun deleteStaleInvalidPaths(days: Int) {
        val cutoff = System.currentTimeMillis() - (days * 24L * 60L * 60L * 1000L)
        imagePathDao.deleteStaleInvalidPaths(cutoff)
    }

    suspend fun getAllPathsSync(): List<ImagePath> {
        return imagePathDao.getAllPathsSync()
    }

    suspend fun delete(hash: String) {
        imageDataDao.deleteByHash(hash)
    }

    suspend fun deletePath(hash: String, path: String) {
        imagePathDao.deletePath(hash, path)
    }

    suspend fun addTagsToImage(hash: String, tags: List<String>) {
        val imageTags = tags.map { ImageTag(hash = hash, tag = it) }
        imageTagDao.insertTags(imageTags)
    }

    suspend fun removeTagFromImage(hash: String, tag: String) {
        imageTagDao.deleteTag(hash, tag)
    }

    suspend fun getTagsForHashSync(hash: String): List<String> {
        return imageTagDao.getTagsForHashSync(hash).map { it.tag }
    }

    suspend fun searchByTag(query: String): List<String> {
        return imageTagDao.searchByTag(query)
    }

    suspend fun searchContactsByName(query: String): List<Contact> {
        return contactDao?.searchContacts(query) ?: emptyList()
    }

    suspend fun getOrCreateContact(name: String): Long {
        val dao = contactDao ?: return -1
        val existing = dao.getContactByName(name)
        return existing?.id ?: dao.insertContact(Contact(name = name))
    }

    suspend fun addContactsToImage(hash: String, contactNames: List<String>) {
        val dao = contactDao ?: return
        for (name in contactNames) {
            val contactId = getOrCreateContact(name)
            if (contactId >= 0) {
                dao.insertImageContact(ImageContact(hash = hash, contactId = contactId))
            }
        }
    }

    suspend fun removeContactFromImage(hash: String, contactId: Long) {
        contactDao?.deleteImageContact(hash, contactId)
    }

    suspend fun getContactsForImage(hash: String): List<Contact> {
        val dao = contactDao ?: return emptyList()
        val imageContacts = dao.getContactsForHash(hash)
        return imageContacts.mapNotNull { dao.getContactById(it.contactId) }
    }

    suspend fun getImageHashesForContact(contactId: Long): List<String> {
        return contactDao?.getHashesForContact(contactId) ?: emptyList()
    }

    fun getContactsWithCount(): Flow<List<ContactWithCount>> {
        return contactDao?.getContactsWithCount()
            ?: kotlinx.coroutines.flow.flowOf(emptyList())
    }

    suspend fun exportBackupData(): BackupData {
        val dao = contactDao
        val allImages = imageDataDao.getAllImagesForBackup()
        val allTags = imageTagDao.getAllTagsForBackup().groupBy { it.hash }
        val allImageContacts = dao?.getAllImageContactsForBackup()?.groupBy { it.hash } ?: emptyMap()
        val allContacts = dao?.getAllContactsForBackup() ?: emptyList()

        val backupImages = allImages.map { img ->
            BackupImageData(
                hash = img.hash,
                remark = img.remark,
                mediaType = img.mediaType,
                createdAt = img.createdAt,
                updatedAt = img.updatedAt,
                tags = allTags[img.hash]?.map { it.tag } ?: emptyList(),
                contactIds = allImageContacts[img.hash]?.map { it.contactId } ?: emptyList()
            )
        }

        val backupContacts = allContacts.map { c ->
            BackupContact(id = c.id, name = c.name, createdAt = c.createdAt)
        }

        val now = System.currentTimeMillis()
        val exportDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date(now))
        return BackupData(
            version = 1,
            exportDate = exportDate,
            exportDateMs = now,
            images = backupImages,
            contacts = backupContacts
        )
    }

    suspend fun importData(json: String): ImportResult {
        val dao = contactDao
        val backup = BackupManager.fromJson(json)

        // Build old contact id → new contact id map
        val idMap = mutableMapOf<Long, Long>()
        var contactsRestored = 0
        for (bc in backup.contacts) {
            val existing = dao?.getContactByName(bc.name)
            val newId = existing?.id ?: dao?.insertContact(Contact(name = bc.name, createdAt = bc.createdAt))
            if (newId != null) {
                idMap[bc.id] = newId
                if (existing == null) contactsRestored++
            }
        }

        var imagesRestored = 0
        var skipped = 0
        for (bi in backup.images) {
            val existing = imageDataDao.getByHash(bi.hash)
            imageDataDao.insertIfNotExists(
                ImageData(
                    hash = bi.hash,
                    remark = bi.remark,
                    mediaType = bi.mediaType,
                    createdAt = bi.createdAt,
                    updatedAt = bi.updatedAt
                )
            )
            if (existing == null) {
                imagesRestored++
            } else {
                if (existing.remark.isBlank() && bi.remark.isNotBlank()) {
                    imageDataDao.updateRemark(bi.hash, bi.remark)
                }
                skipped++
            }

            if (bi.tags.isNotEmpty()) {
                imageTagDao.insertTags(bi.tags.map { ImageTag(hash = bi.hash, tag = it) })
            }

            for (oldContactId in bi.contactIds) {
                val newId = idMap[oldContactId] ?: continue
                dao?.insertImageContact(ImageContact(hash = bi.hash, contactId = newId))
            }
        }

        return ImportResult(
            imagesRestored = imagesRestored,
            contactsRestored = contactsRestored,
            skipped = skipped
        )
    }
}
