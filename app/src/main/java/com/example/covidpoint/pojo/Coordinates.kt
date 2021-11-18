package com.example.covidpoint.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String?,
    @SerializedName("longitude")
    val longitude: String?
)
