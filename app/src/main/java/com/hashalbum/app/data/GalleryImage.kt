package com.hashalbum.app.data

import android.net.Uri

enum class MediaType { IMAGE, VIDEO }

/**
 * Data class representing an image or video in the gallery view.
 * Combines the Uri for display with the hash for identification.
 */
data class GalleryImage(
    val uri: Uri,
    val hash: String? = null,
    val displayName: String = "",
    val dateModified: Long = 0L,
    val size: Long = 0L,
    val mediaType: MediaType = MediaType.IMAGE,
    val duration: Long = 0L
)
