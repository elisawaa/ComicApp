package com.elisawaa.comic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elisawaa.comic.data.model.Comic

@Database(entities = [Comic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun comicDao(): ComicDao
}