package com.example.covidpoint.data.datasource

import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.datasource.interfaces.DatabaseSource
import javax.inject.Inject

class DatabaseSourceImpl @Inject constructor(private val dao: CountriesDao) :
    DatabaseSource {

    override suspend fun getCountries(): List<CountryEntity> =
        dao.getAllCountries()

    override suspend fun insertCountries(countries: List<CountryEntity>) =
        dao.insertCountries(countries)
}