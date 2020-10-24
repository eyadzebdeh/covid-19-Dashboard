package com.eyad.covid_19dashboard.domain.usecase

import com.eyad.covid_19dashboard.domain.model.NewsResponse
import com.eyad.covid_19dashboard.domain.repository.CovidRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val covidRepository: CovidRepository){

    sealed class Result {
        object Loading : Result()
        data class Success(val success: NewsResponse) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(page: Int, countryCode: String): Observable<Result> {
        return covidRepository.getNews(page, countryCode)
            .toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}