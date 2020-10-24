package com.eyad.covid_19dashboard.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

data class NewsResponse(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>
)

@Parcelize
data class Article(
    @SerializedName("source") val source: ArticleSource?,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: Date?,
    @SerializedName("content") val content: String?,
    @SerializedName("url") val url: String
    ) : Parcelable

@Parcelize
data class ArticleSource(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
    ) : Parcelable
