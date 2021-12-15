package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import io.reactivex.rxjava3.core.Single

interface NetworkRepository {

    fun getCountries(): Single<CountriesResponse>

    fun getCountryStatistic(id: Int): Single<CountryResponse>
}