package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(private val countryApiService: CountryApiService) :
    NetworkRepository {

    override suspend fun getCountries(): Result<CountriesResponse> {
        return try {
            val result = countryApiService.getCountries()
            Result.Success(result)
        } catch (e: HttpException) {
            Result.Error(e)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }


    override suspend fun getCountryStatistic(id: Int): Result<CountryResponse> {
        return try {
            val result = countryApiService.getCountryStatistics(id)
            Result.Success(result)
        } catch (e: HttpException) {
            Result.Error(e)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }
}