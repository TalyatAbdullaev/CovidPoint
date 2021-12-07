package com.example.covidpoint.data.pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Statistic(
    @SerializedName("confirmed")
    val confirmed: Int,

    @SerializedName("deaths")
    val deaths: Int,

    @SerializedName("recovered")
    val recovered: Int
): Parcelable
