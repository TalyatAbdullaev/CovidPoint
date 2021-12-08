package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface DatabaseRepository {

    fun insertCountries(countries: List<CountryEntity>): Completable

    fun getCountries(): Single<List<CountryEntity>>

    fun getCountryByCoordinates(longitude: Double, latitude: Double): Single<CountryEntity>
}