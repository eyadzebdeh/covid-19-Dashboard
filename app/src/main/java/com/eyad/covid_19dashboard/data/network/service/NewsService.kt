package com.eyad.covid_19dashboard.data.network.service

import com.eyad.covid_19dashboard.domain.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface NewsService{

    @GET("top-headlines?category=health&apiKey=244968a106424472a2f850e494421108")
    fun getNews(@Query("page") page: Int, @Query("country") countryCode: String): Single<NewsResponse>

}