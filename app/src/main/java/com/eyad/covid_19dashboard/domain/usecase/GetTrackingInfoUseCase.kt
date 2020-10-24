package com.eyad.covid_19dashboard.domain.usecase

import com.eyad.covid_19dashboard.domain.model.TrackingInfoResponse
import com.eyad.covid_19dashboard.domain.repository.CovidRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTrackingInfoUseCase @Inject constructor(private val covidRepository: CovidRepository){

    sealed class Result {
        object Loading : Result()
        data class Success(val success: TrackingInfoResponse) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(date: String): Observable<Result> {
        return covidRepository.getTrackingInfo(date)
            .toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}