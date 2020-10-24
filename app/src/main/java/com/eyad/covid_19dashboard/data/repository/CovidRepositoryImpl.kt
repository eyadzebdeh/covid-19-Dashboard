package com.eyad.covid_19dashboard.data.repository

import com.eyad.covid_19dashboard.data.network.CovidApi
import com.eyad.covid_19dashboard.domain.model.CountryInfoResponse
import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.domain.model.TrackingInfoResponse
import com.eyad.covid_19dashboard.domain.repository.CovidRepository
import io.reactivex.Single

class CovidRepositoryImpl(private val covidApi: CovidApi): CovidRepository{

    override fun getTrackingInfo(date: String): Single<TrackingInfoResponse> {
        return covidApi.getTrackingInfo(date)
    }

    override fun getNews(page: Int, countryCode: String): Single<NewsResponse> {
        return covidApi.getNews(page, countryCode)
    }

    override fun getCountryInfo(countryName: String): Single<List<CountryInfoResponse>> {
        return covidApi.getCountryInfo(countryName)
    }

}