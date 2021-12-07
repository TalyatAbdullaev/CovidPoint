package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.DatabaseRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val dao: CountriesDao) :
    DatabaseRepository {

    override fun getCountries(): List<Country> =
        dao.getAllCountries()

    override fun insertCountries(countries: List<Country>) =
        dao.insertCountries(countries)
}