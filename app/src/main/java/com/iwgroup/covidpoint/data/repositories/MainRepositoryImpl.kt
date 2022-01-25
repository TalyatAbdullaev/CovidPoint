package com.iwgroup.covidpoint.data.repositories

import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.CountryResponse
import com.iwgroup.covidpoint.data.pojo.CountriesResponse
import com.iwgroup.covidpoint.data.datasource.interfaces.DatabaseSource
import com.iwgroup.covidpoint.data.repositories.interfaces.MainRepository
import com.iwgroup.covidpoint.data.datasource.interfaces.NetworkSource
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

    override suspend fun addDataToDB(country: CountryEntity) {
        databaseSource.insertCountry(country)
    }
}