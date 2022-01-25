package com.iwgroup.covidpoint.data.datasource

import com.iwgroup.covidpoint.data.database.countries.CountriesDao
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.datasource.interfaces.DatabaseSource
import javax.inject.Inject

class DatabaseSourceImpl @Inject constructor(private val dao: CountriesDao) :
    DatabaseSource {

    override suspend fun getCountries(): List<CountryEntity> =
        dao.getAllCountries()

    override suspend fun insertCountries(countries: List<CountryEntity>) =
        dao.insertCountries(countries)

    override suspend fun insertCountry(country: CountryEntity) {
        dao.insertCountry(country)
    }
}