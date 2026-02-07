package com.hashalbum.app.util

import android.content.Context
import android.net.Uri

object PathValidator {

    fun isPathValid(context: Context, uri: Uri): Boolean {
        return try {
            context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                cursor.count > 0
            } ?: false
        } catch (e: Exception) {
            false
        }
    }
}
