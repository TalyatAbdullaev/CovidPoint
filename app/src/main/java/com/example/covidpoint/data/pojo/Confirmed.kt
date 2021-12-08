package com.example.covidpoint.data.pojo

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Confirmed(

    @SerializedName("timeline")
    val timeline: List<Int>?
)
