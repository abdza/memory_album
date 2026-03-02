package com.hashalbum.app.util

import com.hashalbum.app.data.BackupContact
import com.hashalbum.app.data.BackupData
import com.hashalbum.app.data.BackupImageData
import org.json.JSONArray
import org.json.JSONObject

object BackupManager {

    private const val SUPPORTED_VERSION = 1

    fun toJson(backup: BackupData): String {
        val root = JSONObject()
        root.put("version", backup.version)
        root.put("exportDate", backup.exportDate)
        root.put("exportDateMs", backup.exportDateMs)

        val imagesArray = JSONArray()
        for (img in backup.images) {
            val obj = JSONObject()
            obj.put("hash", img.hash)
            obj.put("remark", img.remark)
            obj.put("mediaType", img.mediaType)
            obj.put("createdAt", img.createdAt)
            obj.put("updatedAt", img.updatedAt)
            val tagsArray = JSONArray()
            img.tags.forEach { tagsArray.put(it) }
            obj.put("tags", tagsArray)
            val contactIdsArray = JSONArray()
            img.contactIds.forEach { contactIdsArray.put(it) }
            obj.put("contactIds", contactIdsArray)
            imagesArray.put(obj)
        }
        root.put("images", imagesArray)

        val contactsArray = JSONArray()
        for (c in backup.contacts) {
            val obj = JSONObject()
            obj.put("id", c.id)
            obj.put("name", c.name)
            obj.put("createdAt", c.createdAt)
            contactsArray.put(obj)
        }
        root.put("contacts", contactsArray)

        return root.toString(2)
    }

    fun fromJson(json: String): BackupData {
        val root = JSONObject(json)
        val version = root.getInt("version")
        if (version != SUPPORTED_VERSION) {
            throw IllegalArgumentException("Unsupported backup version: $version")
        }

        val exportDate = root.getString("exportDate")
        val exportDateMs = root.getLong("exportDateMs")

        val imagesArray = root.getJSONArray("images")
        val images = mutableListOf<BackupImageData>()
        for (i in 0 until imagesArray.length()) {
            val obj = imagesArray.getJSONObject(i)
            val tagsArray = obj.getJSONArray("tags")
            val tags = (0 until tagsArray.length()).map { tagsArray.getString(it) }
            val contactIdsArray = obj.getJSONArray("contactIds")
            val contactIds = (0 until contactIdsArray.length()).map { contactIdsArray.getLong(it) }
            images.add(
                BackupImageData(
                    hash = obj.getString("hash"),
                    remark = obj.optString("remark", ""),
                    mediaType = obj.optString("mediaType", "image"),
                    createdAt = obj.getLong("createdAt"),
                    updatedAt = obj.getLong("updatedAt"),
                    tags = tags,
                    contactIds = contactIds
                )
            )
        }

        val contactsArray = root.getJSONArray("contacts")
        val contacts = mutableListOf<BackupContact>()
        for (i in 0 until contactsArray.length()) {
            val obj = contactsArray.getJSONObject(i)
            contacts.add(
                BackupContact(
                    id = obj.getLong("id"),
                    name = obj.getString("name"),
                    createdAt = obj.getLong("createdAt")
                )
            )
        }

        return BackupData(
            version = version,
            exportDate = exportDate,
            exportDateMs = exportDateMs,
            images = images,
            contacts = contacts
        )
    }
}
