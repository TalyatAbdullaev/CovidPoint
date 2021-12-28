package com.example.covidpoint.data.network.services

import com.example.covidpoint.data.network.utils.RequestField
import com.example.covidpoint.data.network.utils.Urls
import com.example.covidpoint.data.pojo.CountriesResponse
import com.example.covidpoint.data.pojo.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET(Urls.GET_COUNTRIES_ENDPOINT)
    suspend fun getCountries(): CountriesResponse

    @GET(Urls.GET_COUNTRY_BY_ID_ENDPOINT)
    suspend fun getCountryStatistics(@Path(RequestField.ID) id: Int): CountryResponse
}