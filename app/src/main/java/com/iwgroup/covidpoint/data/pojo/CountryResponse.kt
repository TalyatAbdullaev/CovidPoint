package com.iwgroup.covidpoint.data.pojo

import com.google.gson.annotations.SerializedName

data class CountryResponse(

    @SerializedName("location")
    val location: Country
)