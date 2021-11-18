package com.example.covidpoint.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Statistic(
    @SerializedName("confirmed")
    val confirmed: Int?,
    @SerializedName("deaths")
    val deaths: Int?,
    @SerializedName("recovered")
    val recovered: Int?
)
