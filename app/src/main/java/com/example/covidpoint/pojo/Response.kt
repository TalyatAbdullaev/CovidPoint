package com.example.covidpoint.pojo

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("locations")
    val locations: List<Country>
)
