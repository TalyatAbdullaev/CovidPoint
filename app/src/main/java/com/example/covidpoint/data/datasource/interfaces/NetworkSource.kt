package com.example.covidpoint.data.datasource.interfaces

import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import retrofit2.Response

interface NetworkSource {

    suspend fun getCountries(): Result<CountriesResponse>

    suspend fun getCountryStatistic(id: Int): Result<CountryResponse>
}