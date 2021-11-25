package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Country
import io.reactivex.rxjava3.core.Single

interface GetCountryStatisticRepository {
    fun getCountryStatistic(id: Int): Single<Country>
}