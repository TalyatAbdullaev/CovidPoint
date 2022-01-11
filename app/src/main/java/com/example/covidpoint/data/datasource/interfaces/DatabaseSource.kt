package com.example.covidpoint.data.datasource.interfaces

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DatabaseSource {

    suspend fun insertCountries(countries: List<CountryEntity>)

    suspend fun getCountries(): List<CountryEntity>
}