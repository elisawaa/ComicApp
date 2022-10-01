package com.elisawaa.comic.di

import android.content.Context
import androidx.room.Room
import com.elisawaa.comic.data.local.AppDatabase
import com.elisawaa.comic.data.local.ComicDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideComicDao(appDatabase: AppDatabase): ComicDao {
        return appDatabase.comicDao()
    }
}