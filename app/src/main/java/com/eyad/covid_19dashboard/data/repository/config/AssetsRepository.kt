package com.eyad.covid_19dashboard.data.repository.config

import com.eyad.covid_19dashboard.domain.model.CountryTrackingInfo
import io.reactivex.Single

interface AssetsRepository{
    fun getPolygonsForCountries(countries: Map<String, CountryTrackingInfo>): Single<Map<String, CountryTrackingInfo>>
}