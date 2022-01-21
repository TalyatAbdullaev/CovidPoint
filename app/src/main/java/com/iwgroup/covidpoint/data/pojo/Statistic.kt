package com.iwgroup.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class Statistic(

    @SerializedName("confirmed")
    val confirmed: Int,

    @SerializedName("deaths")
    val deaths: Int,

    @SerializedName("recovered")
    val recovered: Int
)
