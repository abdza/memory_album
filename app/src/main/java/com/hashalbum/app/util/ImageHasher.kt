package com.hashalbum.app.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.security.MessageDigest

/**
 * Utility class for generating SHA-256 hashes of images.
 * The hash is based on the actual image content, so even if the image
 * is moved to a different location, the same hash will be generated.
 */
object ImageHasher {
    
    private const val BUFFER_SIZE = 8192
    private const val ALGORITHM = "SHA-256"
    
    /**
     * Generate a SHA-256 hash of the image content from a Uri.
     * This hash is based on the actual bytes of the image, not its location.
     */
    suspend fun generateHash(context: Context, uri: Uri): String? {
        return withContext(Dispatchers.IO) {
            try {
                val contentResolver: ContentResolver = context.contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                
                inputStream?.use { stream ->
                    val digest = MessageDigest.getInstance(ALGORITHM)
                    val buffer = ByteArray(BUFFER_SIZE)
                    var bytesRead: Int
                    
                    while (stream.read(buffer).also { bytesRead = it } != -1) {
                        digest.update(buffer, 0, bytesRead)
                    }
                    
                    val hashBytes = digest.digest()
                    bytesToHex(hashBytes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    
    /**
     * Generate a partial hash using only the first portion of the image.
     * Useful for quick comparison but less accurate for modified images.
     */
    suspend fun generateQuickHash(context: Context, uri: Uri, maxBytes: Int = 64 * 1024): String? {
        return withContext(Dispatchers.IO) {
            try {
                val contentResolver: ContentResolver = context.contentResolver
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                
                inputStream?.use { stream ->
                    val digest = MessageDigest.getInstance(ALGORITHM)
                    val buffer = ByteArray(BUFFER_SIZE)
                    var bytesRead: Int = 0
                    var totalBytesRead = 0

                    while (totalBytesRead < maxBytes && stream.read(buffer).also { bytesRead = it } != -1) {
                        val bytesToProcess = minOf(bytesRead, maxBytes - totalBytesRead)
                        digest.update(buffer, 0, bytesToProcess)
                        totalBytesRead += bytesToProcess
                    }
                    
                    val hashBytes = digest.digest()
                    bytesToHex(hashBytes)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    
    /**
     * Convert byte array to hexadecimal string.
     */
    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = HEX_CHARS[v ushr 4]
            hexChars[i * 2 + 1] = HEX_CHARS[v and 0x0F]
        }
        return String(hexChars)
    }
    
    private val HEX_CHARS = "0123456789abcdef".toCharArray()
}
