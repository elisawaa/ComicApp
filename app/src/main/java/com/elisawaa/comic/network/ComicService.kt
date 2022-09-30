package com.elisawaa.comic.network

import com.elisawaa.comic.data.model.Comic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ComicService {

    @GET("info.0.json")
    suspend fun getRecentComic() : Response<Comic>

    @GET("{comic_id}/info.0.json")
    suspend fun getComic(@Path("comic_id") id: Int) : Response<Comic>
}