package com.example.covidpoint.data.repositories

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Response
import com.example.covidpoint.data.repositories.interfaces.DatabaseRepository
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository
) : MainRepository {

    override fun getDataFromNetwork(): Single<Response> =
        networkRepository.getCountries()

    override fun getDataFromDB(): List<Country> =
        databaseRepository.getCountries()

    override fun getData() {
        getDataFromNetwork()
            .subscribeOn(Schedulers.io())
            .map { it.locations }
            .subscribe({
                databaseRepository.insertCountries(it)
            }, {

            })
    }
}