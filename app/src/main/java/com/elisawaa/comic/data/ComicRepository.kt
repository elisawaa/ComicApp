package com.elisawaa.comic.data

import com.elisawaa.comic.data.local.ComicDao
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.data.model.ResponseState
import com.elisawaa.comic.network.ComicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class ComicRepository @Inject constructor(
    private val comicService: ComicService,
    private val comicDao: ComicDao
) {

    suspend fun fetchRecentComic(): Flow<ResponseState<Comic>> {
        return flow {
            emit(ResponseState.Loading)

            val response = comicService.getRecentComic()
            val recentComic = response.body()
            if (response.isSuccessful && recentComic != null) {
                emit(ResponseState.Success(recentComic))
            } else {
                emit(ResponseState.Error(Throwable(response.errorBody().toString())))
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchComic(id: Int): Flow<ResponseState<Comic>> {
        return flow {
            emit(ResponseState.Loading)

            val response = comicService.getComic(id)
            val comic = response.body()
            if (response.isSuccessful && comic != null) {
                comicDao.insert(comic)

                emit(ResponseState.Success(comicDao.getComic(comic.id)))
            } else {
                emit(ResponseState.Error(Throwable(response.errorBody().toString())))
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchFavorites(): Flow<ResponseState<List<Comic>>> {
        return flow {
            emit(ResponseState.Loading)
            val favorites = comicDao.getFavorites()
            emit(ResponseState.Success(favorites))

        }.flowOn(Dispatchers.IO)
    }

    private suspend fun updateComicCacheFrom(startId: Int, endId: Int) {
        val comicList = mutableListOf<Comic>()

        for (id in startId..endId) {
            comicService.getComic(id).body()?.let { comicList.add(it) }
        }

        comicDao.insertAll(comicList)
    }

    suspend fun fetchAllComics(): Flow<ResponseState<List<Comic>>> {
        val response = comicService.getRecentComic()
        val recentComic = response.body()

        return if (response.isSuccessful && recentComic != null) {
            val highestComicId = comicDao.getRecentComicId()?.id ?: 0

            updateComicCacheFrom(highestComicId, recentComic.id)

            comicDao.getAll().map {
                ResponseState.Success(it)
            }
        } else {
            flowOf(ResponseState.Error(Throwable(response.message())))
        }
    }

    fun updateFavorite(comic: Comic) {
        comicDao.updateComic(comic)
    }
}
