package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.function.DoubleBinaryOperator

interface MainRepository {

    fun getDataFromNetwork(): Single<Response>

    fun getDataFromDB(): Single<List<CountryEntity>>

    fun addDataToDB(countries: List<CountryEntity>) : Completable

    fun getDataFromNetworkById(id: Int): Single<Country>

    fun getDataFromDBByCoordinates(longitude: Double, latitude: Double): Single<CountryEntity>
}