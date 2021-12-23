package com.example.covidpoint.data.network.services

import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.network.utils.RequestField
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET(Urls.GET_COUNTRIES_ENDPOINT)
    suspend fun getCountries(): Result<CountriesResponse>

    @GET(Urls.GET_COUNTRY_BY_ID_ENDPOINT)
    suspend fun getCountryStatistics(@Path(RequestField.ID) id: Int): Result<CountryResponse>
}