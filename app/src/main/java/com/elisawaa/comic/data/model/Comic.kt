package com.elisawaa.comic.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Comic(
    @SerializedName("num") @PrimaryKey val id: Int,
    @SerializedName("link") val link: String,
    @SerializedName("title") val title: String,
    @SerializedName("safe_title") val safeTitle: String,
    @SerializedName("img") val img: String,
    @SerializedName("alt") val alt: String,
    @SerializedName("transcript") val transcript: String,
    @SerializedName("day") val day: String,
    @SerializedName("month") val month: String,
    @SerializedName("year") val year: String,
    @SerializedName("news") val news: String,
    val favorited: Boolean = false
)