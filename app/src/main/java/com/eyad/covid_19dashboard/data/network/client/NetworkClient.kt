package com.eyad.covid_19dashboard.data.network.client

import retrofit2.Retrofit

abstract class NetworkClient(private val retrofitBuilder: Retrofit.Builder) {

    protected abstract val baseUrl: String

    fun build(): Retrofit {
        return retrofitBuilder.baseUrl(baseUrl).build()
    }
}