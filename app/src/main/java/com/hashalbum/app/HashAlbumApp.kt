package com.hashalbum.app

import android.app.Application
import com.hashalbum.app.data.AppDatabase

class HashAlbumApp : Application() {
    
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}
