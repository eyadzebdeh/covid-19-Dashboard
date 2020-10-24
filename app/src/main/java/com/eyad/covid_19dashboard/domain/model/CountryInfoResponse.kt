package com.eyad.covid_19dashboard.domain.model

import com.google.gson.annotations.SerializedName

data class CountryInfoResponse(
    @SerializedName("alpha2Code") val alpha2Code: String
)