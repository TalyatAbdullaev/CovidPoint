package com.iwgroup.covidpoint.data.datasource.interfaces

import com.iwgroup.covidpoint.data.database.countries.CountryEntity

interface DatabaseSource {

    suspend fun insertCountries(countries: List<CountryEntity>)

    suspend fun insertCountry(country: CountryEntity)

    suspend fun getCountries(): List<CountryEntity>
}