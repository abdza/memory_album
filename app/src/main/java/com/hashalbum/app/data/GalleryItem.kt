package com.hashalbum.app.data

sealed class GalleryItem {
    data class DateHeader(val label: String) : GalleryItem()
    data class ImageItem(val image: GalleryImage) : GalleryItem()
}
