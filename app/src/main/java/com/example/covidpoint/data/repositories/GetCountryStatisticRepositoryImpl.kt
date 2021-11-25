package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.GetCountryStatisticRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCountryStatisticRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetCountryStatisticRepository {
    override fun getCountryStatistic(id: Int): Single<Country> {
        return apiService.getCountryStatistics(id)
    }
}