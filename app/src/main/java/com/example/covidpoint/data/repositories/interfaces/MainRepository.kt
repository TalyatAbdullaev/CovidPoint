package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MainRepository {

    fun getDataFromNetwork(): Single<Response>

    fun getDataFromDB(): Single<List<Country>>

    fun addDataToDB(countries: List<Country>) : Completable
}