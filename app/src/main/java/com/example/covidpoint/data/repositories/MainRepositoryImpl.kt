package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.datasource.interfaces.DatabaseSource
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.data.datasource.interfaces.NetworkSource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val networkSource: NetworkSource,
    private val databaseSource: DatabaseSource
) : MainRepository {

    override suspend fun getDataFromDB(): List<CountryEntity> =
        databaseSource.getCountries()

    override suspend fun addDataToDB(countries: List<CountryEntity>) =
        databaseSource.insertCountries(countries)

    override suspend fun getDataFromNetwork(): Result<CountriesResponse> =
        networkSource.getCountries()

    override suspend fun getDataFromNetworkById(id: Int): Result<CountryResponse> =
        networkSource.getCountryStatistic(id)
}