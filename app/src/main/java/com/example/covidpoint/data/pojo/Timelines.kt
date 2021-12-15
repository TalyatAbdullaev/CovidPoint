package com.example.covidpoint.data.pojo

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class Timelines(

    @SerializedName("confirmed")
    val confirmed: Confirmed
)