package com.eyad.covid_19dashboard.data.network.client

import retrofit2.Retrofit

class NewsClient(
    retrofitBuilder: Retrofit.Builder
) : NetworkClient(retrofitBuilder) {

    override val baseUrl = "https://newsapi.org/v2/"
}