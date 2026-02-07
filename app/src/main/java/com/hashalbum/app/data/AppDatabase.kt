package com.hashalbum.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ImageData::class, ImagePath::class, ImageTag::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDataDao(): ImageDataDao
    abstract fun imagePathDao(): ImagePathDao
    abstract fun imageTagDao(): ImageTagDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `image_tags` (
                        `hash` TEXT NOT NULL,
                        `tag` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        PRIMARY KEY(`hash`, `tag`),
                        FOREIGN KEY(`hash`) REFERENCES `image_data`(`hash`) ON DELETE CASCADE
                    )"""
                )
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `image_paths` (
                        `hash` TEXT NOT NULL,
                        `path` TEXT NOT NULL,
                        `lastSeen` INTEGER NOT NULL DEFAULT 0,
                        `isValid` INTEGER NOT NULL DEFAULT 1,
                        PRIMARY KEY(`hash`, `path`),
                        FOREIGN KEY(`hash`) REFERENCES `image_data`(`hash`) ON DELETE CASCADE
                    )"""
                )
                db.execSQL(
                    """INSERT OR IGNORE INTO `image_paths` (`hash`, `path`, `lastSeen`, `isValid`)
                       SELECT `hash`, `lastKnownPath`, `updatedAt`, 1
                       FROM `image_data`
                       WHERE `lastKnownPath` IS NOT NULL AND `lastKnownPath` != ''"""
                )
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hash_album_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
