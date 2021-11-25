package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.pojo.Response
import com.example.covidpoint.data.repositories.interfaces.GetCountriesRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetCountriesRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetCountriesRepository {
    override fun getCountries(): Single<Response> {
        return apiService.getCountries()
    }
}