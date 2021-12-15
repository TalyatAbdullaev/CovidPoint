package com.example.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class CountriesResponse(

    @SerializedName("locations")
    val locations: List<Country>
)
