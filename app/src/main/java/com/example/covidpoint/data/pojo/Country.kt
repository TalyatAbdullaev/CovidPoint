package com.example.covidpoint.data.pojo

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "countries")
data class Country(
    @SerializedName("id")
    @PrimaryKey
    val id: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("country_code")
    val countryCode: String,

    @SerializedName("country_population")
    val countryPopulation: Int,

    @SerializedName("province")
    val province: String,

    @SerializedName("coordinates")
    val coordinates: Coordinates,

    @SerializedName("latest")
    val latest: Statistic
): Parcelable