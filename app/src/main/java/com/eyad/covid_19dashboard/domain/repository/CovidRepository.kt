package com.eyad.covid_19dashboard.domain.repository

import com.eyad.covid_19dashboard.domain.model.CountryInfoResponse
import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.domain.model.TrackingInfoResponse
import io.reactivex.Single


interface CovidRepository {
    fun getTrackingInfo(date: String): Single<TrackingInfoResponse>
    fun getNews(page: Int, countryCode: String): Single<NewsResponse>
    fun getCountryInfo(countryName: String): Single<List<CountryInfoResponse>>
}