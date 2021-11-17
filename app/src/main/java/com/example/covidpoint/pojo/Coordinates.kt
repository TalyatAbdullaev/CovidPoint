package com.example.covidpoint.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("latitude")
    @Expose
    val latitude: String?,
    @SerializedName("longitude")
    @Expose
    val longitude: String?
)
