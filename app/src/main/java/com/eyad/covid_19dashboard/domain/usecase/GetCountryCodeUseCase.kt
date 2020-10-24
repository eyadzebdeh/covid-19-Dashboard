package com.eyad.covid_19dashboard.domain.usecase

import com.eyad.covid_19dashboard.domain.repository.CovidRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetCountryCodeUseCase @Inject constructor(private val covidRepository: CovidRepository){

    sealed class Result {
        object Loading : Result()
        data class Success(val success: String) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(countryName: String): Observable<Result> {
        return covidRepository.getCountryInfo(countryName)
            .toObservable()
            .map { Result.Success(it[0].alpha2Code) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}