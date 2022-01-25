package com.iwgroup.covidpoint.data.repositories.interfaces

import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.CountryResponse
import com.iwgroup.covidpoint.data.pojo.CountriesResponse
import com.iwgroup.covidpoint.data.pojo.Country

interface MainRepository {

    suspend fun getDataFromDB(): List<CountryEntity>

    suspend fun addDataToDB(countries: List<CountryEntity>)

    suspend fun addDataToDB(country: CountryEntity)

    suspend fun getDataFromNetwork(): Result<CountriesResponse>

    suspend fun getDataFromNetworkById(id: Int): Result<CountryResponse>
}