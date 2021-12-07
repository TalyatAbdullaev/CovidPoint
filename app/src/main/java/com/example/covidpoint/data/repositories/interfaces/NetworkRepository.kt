package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import io.reactivex.rxjava3.core.Single

interface NetworkRepository {

    fun getCountries(): Single<Response>

    fun getCountryStatistic(id: Int): Single<Country>
}