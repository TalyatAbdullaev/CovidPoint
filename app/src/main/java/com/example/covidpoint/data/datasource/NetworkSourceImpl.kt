package com.example.covidpoint.data.datasource

import com.example.covidpoint.data.datasource.interfaces.NetworkSource
import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.pojo.CountryResponse
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class NetworkSourceImpl @Inject constructor(private val countryApiService: CountryApiService) :
    NetworkSource {

    private suspend fun <T> requestHandler(request: suspend () -> T): Result<T> {
        return try {
            val result = request()
            Result.Success(result)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getCountries(): Result<CountriesResponse> =
        requestHandler { countryApiService.getCountries() }

    override suspend fun getCountryStatistic(id: Int): Result<CountryResponse> =
        requestHandler { countryApiService.getCountryStatistics(id) }
}