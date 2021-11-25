package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Response
import io.reactivex.rxjava3.core.Single

interface GetCountriesRepository {
    fun getCountries(): Single<Response>
}