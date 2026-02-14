package com.hashalbum.app.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ImageMetadata(
    val dateTaken: String? = null,
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val camera: String? = null,
    val resolution: String? = null,
    val fileSize: String? = null,
    val fileName: String? = null,
    val duration: String? = null
)

object ImageMetadataHelper {

    suspend fun getMetadata(context: Context, uri: Uri): ImageMetadata {
        return withContext(Dispatchers.IO) {
            val isVideo = uri.toString().contains("/video/")
            if (isVideo) {
                getVideoMetadata(context, uri)
            } else {
                getImageMetadata(context, uri)
            }
        }
    }

    private fun getVideoMetadata(context: Context, uri: Uri): ImageMetadata {
        var dateTaken: String? = null
        var fileSize: String? = null
        var fileName: String? = null
        var resolution: String? = null
        var duration: String? = null

        try {
            val projection = arrayOf(
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DURATION
            )

            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIdx = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)
                    val sizeIdx = cursor.getColumnIndex(MediaStore.Video.Media.SIZE)
                    val dateIdx = cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN)
                    val widthIdx = cursor.getColumnIndex(MediaStore.Video.Media.WIDTH)
                    val heightIdx = cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT)
                    val durationIdx = cursor.getColumnIndex(MediaStore.Video.Media.DURATION)

                    if (nameIdx >= 0) fileName = cursor.getString(nameIdx)
                    if (sizeIdx >= 0) {
                        val size = cursor.getLong(sizeIdx)
                        fileSize = formatFileSize(size)
                    }
                    if (dateIdx >= 0) {
                        val dateMillis = cursor.getLong(dateIdx)
                        if (dateMillis > 0) {
                            dateTaken = formatDate(dateMillis)
                        }
                    }
                    if (widthIdx >= 0 && heightIdx >= 0) {
                        val width = cursor.getInt(widthIdx)
                        val height = cursor.getInt(heightIdx)
                        if (width > 0 && height > 0) {
                            resolution = "${width} \u00D7 ${height}"
                        }
                    }
                    if (durationIdx >= 0) {
                        val durationMs = cursor.getLong(durationIdx)
                        if (durationMs > 0) {
                            duration = formatDuration(durationMs)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ImageMetadata(
            dateTaken = dateTaken,
            resolution = resolution,
            fileSize = fileSize,
            fileName = fileName,
            duration = duration
        )
    }

    private fun getImageMetadata(context: Context, uri: Uri): ImageMetadata {
        var dateTaken: String? = null
        var location: String? = null
        var latitude: Double? = null
        var longitude: Double? = null
        var camera: String? = null
        var resolution: String? = null
        var fileSize: String? = null
        var fileName: String? = null

        // Get basic info from MediaStore
        try {
            val projection = arrayOf(
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT
            )

            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val nameIdx = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                    val sizeIdx = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
                    val dateIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
                    val widthIdx = cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)
                    val heightIdx = cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)

                    if (nameIdx >= 0) fileName = cursor.getString(nameIdx)
                    if (sizeIdx >= 0) {
                        val size = cursor.getLong(sizeIdx)
                        fileSize = formatFileSize(size)
                    }
                    if (dateIdx >= 0) {
                        val dateMillis = cursor.getLong(dateIdx)
                        if (dateMillis > 0) {
                            dateTaken = formatDate(dateMillis)
                        }
                    }
                    if (widthIdx >= 0 && heightIdx >= 0) {
                        val width = cursor.getInt(widthIdx)
                        val height = cursor.getInt(heightIdx)
                        if (width > 0 && height > 0) {
                            resolution = "${width} \u00D7 ${height}"
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Get EXIF data
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val exif = ExifInterface(inputStream)

                // Get date from EXIF if not available from MediaStore
                if (dateTaken == null) {
                    val exifDate = exif.getAttribute(ExifInterface.TAG_DATETIME_ORIGINAL)
                        ?: exif.getAttribute(ExifInterface.TAG_DATETIME)
                    if (exifDate != null) {
                        dateTaken = formatExifDate(exifDate)
                    }
                }

                // Get GPS coordinates
                val latLong = exif.latLong
                if (latLong != null) {
                    latitude = latLong[0]
                    longitude = latLong[1]
                    location = formatCoordinates(latLong[0], latLong[1])
                }

                // Get camera info
                val make = exif.getAttribute(ExifInterface.TAG_MAKE)
                val model = exif.getAttribute(ExifInterface.TAG_MODEL)
                camera = when {
                    make != null && model != null -> {
                        if (model.startsWith(make, ignoreCase = true)) model
                        else "$make $model"
                    }
                    model != null -> model
                    make != null -> make
                    else -> null
                }

                // Get resolution from EXIF if not available
                if (resolution == null) {
                    val width = exif.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 0)
                    val height = exif.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 0)
                    if (width > 0 && height > 0) {
                        resolution = "${width} \u00D7 ${height}"
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ImageMetadata(
            dateTaken = dateTaken,
            location = location,
            latitude = latitude,
            longitude = longitude,
            camera = camera,
            resolution = resolution,
            fileSize = fileSize,
            fileName = fileName
        )
    }

    private fun formatDate(millis: Long): String {
        val formatter = SimpleDateFormat("EEEE, MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    private fun formatExifDate(exifDate: String): String? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE, MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
            val date = inputFormat.parse(exifDate)
            date?.let { outputFormat.format(it) }
        } catch (e: Exception) {
            exifDate
        }
    }

    private fun formatCoordinates(lat: Double, lon: Double): String {
        val latDir = if (lat >= 0) "N" else "S"
        val lonDir = if (lon >= 0) "E" else "W"
        return String.format(Locale.US, "%.4f\u00B0 %s, %.4f\u00B0 %s",
            kotlin.math.abs(lat), latDir,
            kotlin.math.abs(lon), lonDir)
    }

    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes >= 1024 * 1024 -> String.format(Locale.US, "%.1f MB", bytes / (1024.0 * 1024.0))
            bytes >= 1024 -> String.format(Locale.US, "%.1f KB", bytes / 1024.0)
            else -> "$bytes B"
        }
    }

    private fun formatDuration(millis: Long): String {
        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }
}
