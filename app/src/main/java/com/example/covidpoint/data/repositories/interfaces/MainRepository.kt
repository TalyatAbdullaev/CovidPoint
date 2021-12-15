package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.CountryResponse
import com.example.covidpoint.data.pojo.CountriesResponse
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MainRepository {

    fun getDataFromNetwork(): Single<CountriesResponse>

    fun getDataFromDB(): Single<List<CountryEntity>>

    fun addDataToDB(countries: List<CountryEntity>) : Completable

    fun getDataFromNetworkById(id: Int): Single<CountryResponse>
}