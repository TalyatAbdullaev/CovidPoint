package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val countryApiService: CountryApiService) :
    NetworkRepository {

    override suspend fun getCountries(): Result<CountriesResponse> =
        countryApiService.getCountries()

    override suspend fun getCountryStatistic(id: Int): Result<CountryResponse> =
        countryApiService.getCountryStatistics(id)
}