package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import io.reactivex.rxjava3.core.Single

interface MainRepository {

    fun getDataFromNetwork(): Single<Response>

    fun getDataFromDB(): List<Country>

    fun getData()
}