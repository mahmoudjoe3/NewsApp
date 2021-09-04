package com.mahmoudjoe3.newsapp.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val source: Source,
    val title: String,
    val description: String?,
    val urlToImage: String,
    val publishedAt: String
) : Parcelable {
    @Parcelize
    data class Source(
        val id: String?,
        val name: String
    ) : Parcelable
}