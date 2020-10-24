package com.eyad.covid_19dashboard.data.network.service

import com.eyad.covid_19dashboard.domain.model.CountryInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface CountriesService{

    @GET("name/{name}?fullText=true")
    fun getCountryInfo(@Path("name") name: String): Single<List<CountryInfoResponse>>

}