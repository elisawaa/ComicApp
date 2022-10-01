package com.elisawaa.comic.data

import com.elisawaa.comic.data.local.ComicDao
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.data.model.ResponseState
import com.elisawaa.comic.network.ComicService
import com.elisawaa.comic.network.LOWEST_COMIC_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ComicRepository @Inject constructor(
    private val comicService: ComicService,
    private val comicDao: ComicDao
) {

    suspend fun fetchComic(id: Int): Flow<ResponseState<Comic>> {
        return flow {
            emit(ResponseState.Loading)

            val response = comicService.getComic(id)
            val comic = response.body()
            if (response.isSuccessful && comic != null) {
                comicDao.insert(comic)
                emit(ResponseState.Success(comicDao.getComic(comic.id)))
            } else {
                emit(ResponseState.Error(response.errorBody().toString()))
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

    suspend fun fetchAllComicsFromDb(): List<Comic> {
        return comicDao.getAllNonFlow()
    }

    suspend fun refreshAndGetAllComics(): Flow<ResponseState<List<Comic>>> {
        val response = comicService.getRecentComic()
        val recentComic = response.body()

        return flow {
            if (response.isSuccessful && recentComic != null) {
                // If there is no comicId in the DB, setting it to the lowest comic id to fetch everything
                val highestCachedComicId = comicDao.getRecentComicId()?.id ?: LOWEST_COMIC_ID

                for (id in highestCachedComicId..recentComic.id) {
                    comicService.getComic(id).body()?.let { comicDao.insert(it) }

                    // To avoid a very long loading state on the first refresh we update regularly
                    if (id % 25 == 0) {
                        emit(ResponseState.Success(comicDao.getAllNonFlow()))
                    }
                }
            } else {
                flowOf(ResponseState.Error(response.message()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun updateFavorite(comic: Comic) {
        comicDao.updateComic(comic)
    }
}
