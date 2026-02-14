package com.hashalbum.app.util

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import com.hashalbum.app.data.GalleryImage
import com.hashalbum.app.data.MediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Helper class to load images and videos from device storage using MediaStore.
 */
object MediaStoreHelper {

    /**
     * Load all images from the device.
     */
    suspend fun loadAllImages(context: Context): List<GalleryImage> {
        return withContext(Dispatchers.IO) {
            val images = mutableListOf<GalleryImage>()

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE
            )

            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn) ?: ""
                    val date = cursor.getLong(dateColumn)
                    val size = cursor.getLong(sizeColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    images.add(
                        GalleryImage(
                            uri = contentUri,
                            displayName = name,
                            dateModified = date,
                            size = size,
                            mediaType = MediaType.IMAGE
                        )
                    )
                }
            }

            images
        }
    }

    /**
     * Load all videos from the device.
     */
    suspend fun loadAllVideos(context: Context): List<GalleryImage> {
        return withContext(Dispatchers.IO) {
            val videos = mutableListOf<GalleryImage>()

            val projection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
            )

            val sortOrder = "${MediaStore.Video.Media.DATE_MODIFIED} DESC"

            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn) ?: ""
                    val date = cursor.getLong(dateColumn)
                    val size = cursor.getLong(sizeColumn)
                    val duration = cursor.getLong(durationColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    videos.add(
                        GalleryImage(
                            uri = contentUri,
                            displayName = name,
                            dateModified = date,
                            size = size,
                            mediaType = MediaType.VIDEO,
                            duration = duration
                        )
                    )
                }
            }

            videos
        }
    }

    /**
     * Load all media (images + videos) merged and sorted by dateModified DESC.
     */
    suspend fun loadAllMedia(context: Context): List<GalleryImage> {
        return withContext(Dispatchers.IO) {
            val images = loadAllImages(context)
            val videos = loadAllVideos(context)
            (images + videos).sortedByDescending { it.dateModified }
        }
    }

    /**
     * Load images from a specific folder/bucket.
     */
    suspend fun loadImagesFromBucket(context: Context, bucketId: Long): List<GalleryImage> {
        return withContext(Dispatchers.IO) {
            val images = mutableListOf<GalleryImage>()

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE
            )

            val selection = "${MediaStore.Images.Media.BUCKET_ID} = ?"
            val selectionArgs = arrayOf(bucketId.toString())
            val sortOrder = "${MediaStore.Images.Media.DATE_MODIFIED} DESC"

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn) ?: ""
                    val date = cursor.getLong(dateColumn)
                    val size = cursor.getLong(sizeColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    images.add(
                        GalleryImage(
                            uri = contentUri,
                            displayName = name,
                            dateModified = date,
                            size = size,
                            mediaType = MediaType.IMAGE
                        )
                    )
                }
            }

            // Also load videos from the same bucket
            val videoProjection = arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
            )

            val videoSelection = "${MediaStore.Video.Media.BUCKET_ID} = ?"

            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoProjection,
                videoSelection,
                selectionArgs,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val dateColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn) ?: ""
                    val date = cursor.getLong(dateColumn)
                    val size = cursor.getLong(sizeColumn)
                    val duration = cursor.getLong(durationColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    images.add(
                        GalleryImage(
                            uri = contentUri,
                            displayName = name,
                            dateModified = date,
                            size = size,
                            mediaType = MediaType.VIDEO,
                            duration = duration
                        )
                    )
                }
            }

            images.sortedByDescending { it.dateModified }
        }
    }

    /**
     * Get all media buckets (folders) with their media counts.
     */
    suspend fun getImageBuckets(context: Context): List<ImageBucket> {
        return withContext(Dispatchers.IO) {
            val buckets = mutableMapOf<Long, ImageBucket>()

            // Image buckets
            val projection = arrayOf(
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media._ID
            )

            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null
            )?.use { cursor ->
                val bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
                val bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val bucketId = cursor.getLong(bucketIdColumn)
                    val bucketName = cursor.getString(bucketNameColumn) ?: "Unknown"
                    val imageId = cursor.getLong(idColumn)

                    val existing = buckets[bucketId]
                    if (existing != null) {
                        buckets[bucketId] = existing.copy(imageCount = existing.imageCount + 1)
                    } else {
                        val thumbnailUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            imageId
                        )
                        buckets[bucketId] = ImageBucket(
                            id = bucketId,
                            name = bucketName,
                            thumbnailUri = thumbnailUri,
                            imageCount = 1
                        )
                    }
                }
            }

            // Video buckets
            val videoProjection = arrayOf(
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID
            )

            context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                videoProjection,
                null,
                null,
                null
            )?.use { cursor ->
                val bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID)
                val bucketNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

                while (cursor.moveToNext()) {
                    val bucketId = cursor.getLong(bucketIdColumn)
                    val bucketName = cursor.getString(bucketNameColumn) ?: "Unknown"
                    val videoId = cursor.getLong(idColumn)

                    val existing = buckets[bucketId]
                    if (existing != null) {
                        buckets[bucketId] = existing.copy(imageCount = existing.imageCount + 1)
                    } else {
                        val thumbnailUri = ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            videoId
                        )
                        buckets[bucketId] = ImageBucket(
                            id = bucketId,
                            name = bucketName,
                            thumbnailUri = thumbnailUri,
                            imageCount = 1
                        )
                    }
                }
            }

            buckets.values.toList().sortedByDescending { it.imageCount }
        }
    }
}

data class ImageBucket(
    val id: Long,
    val name: String,
    val thumbnailUri: android.net.Uri,
    val imageCount: Int
)
