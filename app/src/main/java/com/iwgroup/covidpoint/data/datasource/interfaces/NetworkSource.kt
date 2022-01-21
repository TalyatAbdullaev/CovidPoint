package com.iwgroup.covidpoint.data.datasource.interfaces

import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.CountryResponse
import com.iwgroup.covidpoint.data.pojo.CountriesResponse

interface NetworkSource {

    suspend fun getCountries(): Result<CountriesResponse>

    suspend fun getCountryStatistic(id: Int): Result<CountryResponse>
}