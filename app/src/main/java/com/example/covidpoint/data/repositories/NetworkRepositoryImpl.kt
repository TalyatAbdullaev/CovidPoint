package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NetworkRepository {

    override fun getCountries(): Single<Response> =
        apiService.getCountries()

    override fun getCountryStatistic(id: Int): Single<Country> =
        apiService.getCountryStatistics(id)
}