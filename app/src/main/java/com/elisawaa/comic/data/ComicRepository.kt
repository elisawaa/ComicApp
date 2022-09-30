package com.elisawaa.comic.data

import com.elisawaa.comic.data.local.ComicDao
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.data.model.ResponseState
import com.elisawaa.comic.network.ComicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
            val recentComic = response.body()
            if (response.isSuccessful && recentComic != null) {
                emit(ResponseState.Success(recentComic))
            } else {
                emit(ResponseState.Error(Throwable(response.errorBody().toString())))
            }

        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchFavorites(): Flow<ResponseState<List<Comic>>> {
        return flow {
            emit(ResponseState.Loading)
            // TODO EWB
        }.flowOn(Dispatchers.IO)
    }


    suspend fun fetchAllComics(): Flow<ResponseState<List<Comic>>> {
        return flow {
            val response = comicService.getRecentComic()
            val recentComic = response.body()
            if (response.isSuccessful && recentComic != null) {
                // TODO EWB get highest comic id from DB

                val highestComicId = 0
                val comicList = mutableListOf<Comic>()

                for (id in highestComicId..recentComic.id) {
                    comicService.getComic(id).body()?.let { comicList.add(it) }
                    emit(ResponseState.Success(comicList))
                }
                emit(ResponseState.Success(comicList))
            } else {
                emit(ResponseState.Error(Throwable(response.errorBody().toString())))
            }


            // TODO EWB clear db
            // TODO EWB add list to DB
            // Dao auto update?
        }

    }

}
