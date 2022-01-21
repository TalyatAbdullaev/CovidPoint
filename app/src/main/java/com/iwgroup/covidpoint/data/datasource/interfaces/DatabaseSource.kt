package com.iwgroup.covidpoint.data.datasource.interfaces

import com.iwgroup.covidpoint.data.database.CountryEntity

interface DatabaseSource {

    suspend fun insertCountries(countries: List<CountryEntity>)

    suspend fun getCountries(): List<CountryEntity>
}