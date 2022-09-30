package com.elisawaa.comic.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elisawaa.comic.data.model.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {

    @Query("SELECT * FROM comic order by id DESC")
    fun getAll(): Flow<List<Comic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(comics: List<Comic>)

}