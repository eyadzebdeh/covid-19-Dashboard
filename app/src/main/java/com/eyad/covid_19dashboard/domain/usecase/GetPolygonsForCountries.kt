package com.eyad.covid_19dashboard.domain.usecase

import com.eyad.covid_19dashboard.data.repository.config.AssetsRepository
import com.eyad.covid_19dashboard.domain.model.CountryTrackingInfo
import io.reactivex.Observable
import javax.inject.Inject


class GetPolygonsForCountries @Inject constructor(
    private val assetsRepository: AssetsRepository
){

    sealed class Result {
        object Loading : Result()
        data class Success(val success: Map<String, CountryTrackingInfo>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(countries: Map<String, CountryTrackingInfo>): Observable<Result> {
        return assetsRepository.getPolygonsForCountries(countries)
            .toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}