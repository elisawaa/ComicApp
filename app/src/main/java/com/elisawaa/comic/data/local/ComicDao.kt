package com.elisawaa.comic.data.local

import androidx.room.*
import com.elisawaa.comic.data.model.Comic
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {

    @Query("SELECT * FROM comic order by id DESC")
    fun getAll(): Flow<List<Comic>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(comics: List<Comic>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comic: Comic)

    @Query("DELETE FROM comic")
    suspend fun deleteAll()

    @Query("SELECT * FROM comic ORDER BY id DESC limit 1")
    suspend fun getRecentComicId(): Comic?

    @Query("SELECT * FROM comic WHERE id=:id ")
    suspend fun getComic(id: Int): Comic

    @Query("SELECT * FROM comic WHERE favorited = 1")
    suspend fun getFavorites(): List<Comic>

    @Update
    fun updateComic(comic: Comic)

}