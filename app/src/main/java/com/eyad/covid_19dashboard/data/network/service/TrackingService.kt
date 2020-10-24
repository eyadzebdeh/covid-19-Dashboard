package com.eyad.covid_19dashboard.data.network.service

import com.eyad.covid_19dashboard.domain.model.TrackingInfoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface TrackingService{

    @GET("/api?")
    fun getTrackingInfo(@Query("date_from") fromDate: String, @Query("date_to") toDate: String): Single<TrackingInfoResponse>

}