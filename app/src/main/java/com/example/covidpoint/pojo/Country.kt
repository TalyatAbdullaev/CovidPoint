package com.example.covidpoint.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("country")
    @Expose
    val country: String?,
    @SerializedName("country_code")
    @Expose
    val countryCode: String?,
    @SerializedName("country_population")
    @Expose
    val countryPopulation: Int?,
    @SerializedName("province")
    @Expose
    val province: String?,
    @SerializedName("last_updated")
    @Expose
    val lastUpdated: String?,
    @SerializedName("coordinates")
    @Expose
    val coordinates: Coordinates?,
    @SerializedName("latest")
    val latest: Statistic?
)
