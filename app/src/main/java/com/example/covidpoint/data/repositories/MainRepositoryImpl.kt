package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.repositories.interfaces.DatabaseRepository
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : MainRepository {

    override suspend fun getDataFromDB(): List<CountryEntity> =
        databaseRepository.getCountries()

    override suspend fun addDataToDB(countries: List<CountryEntity>) =
        databaseRepository.insertCountries(countries)

    override suspend fun getDataFromNetwork(): Result<CountriesResponse> =
        networkRepository.getCountries()

    override suspend fun getDataFromNetworkById(id: Int): Result<CountryResponse> =
        networkRepository.getCountryStatistic(id)
}