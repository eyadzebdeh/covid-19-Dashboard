package com.eyad.covid_19dashboard.domain.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class TrackingInfoResponse(@SerializedName("dates") val dates: Map<String, TrackingInfoCountries>)

data class TrackingInfoCountries(@SerializedName("countries") val countries: Map<String, CountryTrackingInfo>)

@Parcelize
data class CountryTrackingInfo(@SerializedName("id") val id: String,
                               @SerializedName("name") val name: String,
                               @SerializedName("today_open_cases") val todayOpenCases: Int,
                               @SerializedName("today_new_confirmed") val todayNewCases: Int,
                               @SerializedName("today_new_deaths") val todayDeaths: Int,
                               @SerializedName("today_confirmed") val totalCases: Int,
                               @SerializedName("today_deaths") val totalDeaths: Int,
                               var dangerLevel: Int,
                               var polygons: List<List<LatLng>>?) : Parcelable