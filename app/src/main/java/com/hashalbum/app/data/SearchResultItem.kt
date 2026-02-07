package com.hashalbum.app.data

import android.net.Uri

data class SearchResultItem(
    val imageData: ImageData,
    val paths: List<PathInfo>,
    val tags: List<String> = emptyList()
)

data class PathInfo(
    val uri: Uri,
    val lastSeen: Long,
    val isValid: Boolean
)
