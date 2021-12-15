package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NetworkRepository {

    override fun getCountries(): Single<CountriesResponse> =
        apiService.getCountries()

    override fun getCountryStatistic(id: Int): Single<CountryResponse> =
        apiService.getCountryStatistics(id)
}