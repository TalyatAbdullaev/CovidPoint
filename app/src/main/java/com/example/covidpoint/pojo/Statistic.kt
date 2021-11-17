package com.example.covidpoint.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Statistic(
    @SerializedName("confirmed")
    @Expose
    val confirmed: Int,
    @SerializedName("deaths")
    @Expose
    val deaths: Int,
    @SerializedName("recovered")
    @Expose
    val recovered: Int
)
