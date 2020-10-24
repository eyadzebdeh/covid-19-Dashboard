package com.eyad.covid_19dashboard.data.network.client

import retrofit2.Retrofit

class TrackingClient(
    retrofitBuilder: Retrofit.Builder
) : NetworkClient(retrofitBuilder) {

    override val baseUrl = "https://api.covid19tracking.narrativa.com"
}