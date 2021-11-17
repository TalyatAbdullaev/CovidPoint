package com.example.covidpoint.api

import com.example.covidpoint.pojo.Country
import com.example.covidpoint.pojo.Responce
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("v2/locations")
    fun getCountries(): Single<Responce>

    @GET("v2/locations{id}")
    fun getCountryStatistics(@Path("id") id: Int): Single<Country>
}