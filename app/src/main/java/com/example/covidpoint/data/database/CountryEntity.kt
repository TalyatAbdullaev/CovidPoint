package com.example.covidpoint.data.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = CountriesDatabaseConstants.COUNTRIES_TABLE_NAME)
data class CountryEntity @JvmOverloads constructor(

    @PrimaryKey
    val id: Int,

    val country: String,

    val countryCode: String,

    val countryPopulation: Int,

    val province: String,

    val latitude: Double,

    val longitude: Double,

    val confirmed: Int,

    val deaths: Int,

    val recovered: Int,

    @Ignore
    var confirmedStats: Map<String, Int>? = null
)
