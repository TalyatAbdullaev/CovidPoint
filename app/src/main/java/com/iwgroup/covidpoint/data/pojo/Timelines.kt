package com.iwgroup.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class Timelines(

    @SerializedName("confirmed")
    val confirmed: Confirmed?
)