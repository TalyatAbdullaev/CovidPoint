package com.example.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("country_population")
    val countryPopulation: Int?,
    @SerializedName("province")
    val province: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("coordinates")
    val coordinates: Coordinates?,
    @SerializedName("latest")
    val latest: Statistic?
)
