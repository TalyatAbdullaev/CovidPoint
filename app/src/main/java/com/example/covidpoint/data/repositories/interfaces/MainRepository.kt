package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MainRepository {

    suspend fun getDataFromDB(): List<CountryEntity>

    suspend fun addDataToDB(countries: List<CountryEntity>)

    suspend fun getDataFromNetwork(): Result<CountriesResponse>

    suspend fun getDataFromNetworkById(id: Int): Result<CountryResponse>
}