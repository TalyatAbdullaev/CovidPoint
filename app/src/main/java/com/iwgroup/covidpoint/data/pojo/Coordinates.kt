package com.iwgroup.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class Coordinates(

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String
)
