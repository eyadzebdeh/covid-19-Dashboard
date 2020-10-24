package com.eyad.covid_19dashboard.data.network

import com.eyad.covid_19dashboard.data.network.service.CountriesService
import com.eyad.covid_19dashboard.data.network.service.NewsService
import com.eyad.covid_19dashboard.data.network.service.TrackingService
import com.eyad.covid_19dashboard.domain.model.CountryInfoResponse
import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.domain.model.TrackingInfoResponse
import io.reactivex.Single

class CovidApi constructor(
    private val trackingService: TrackingService,
    private val newsService: NewsService,
    private val countriesService: CountriesService
    ){

    fun getTrackingInfo(date: String): Single<TrackingInfoResponse> {
        return trackingService.getTrackingInfo(date, date)
    }

    fun getNews(page: Int, countryCode: String): Single<NewsResponse> {
        return newsService.getNews(page, countryCode)
    }

    fun getCountryInfo(countryName: String): Single<List<CountryInfoResponse>> {
        return countriesService.getCountryInfo(countryName)
    }
}