package com.eyad.covid_19dashboard.data.network.client

import retrofit2.Retrofit

class CountriesClient(
    retrofitBuilder: Retrofit.Builder
) : NetworkClient(retrofitBuilder) {

    override val baseUrl = "https://restcountries.eu/rest/v2/"
}