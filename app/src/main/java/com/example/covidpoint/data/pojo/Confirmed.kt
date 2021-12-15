package com.example.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class Confirmed(

    @SerializedName("timeline")
    val timeline: Map<String, Int>
)
