package com.example.covidpoint.api

import com.example.covidpoint.pojo.Country
import com.example.covidpoint.pojo.Response
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(Urls.GET_COUNTRIES_ENDPOINT)
    fun getCountries(): Single<Response>

    @GET(Urls.GET_COUNTRY_BY_ID_ENDPOINT)
    fun getCountryStatistics(@Path(RequestField.ID) id: Int): Single<Country>
}