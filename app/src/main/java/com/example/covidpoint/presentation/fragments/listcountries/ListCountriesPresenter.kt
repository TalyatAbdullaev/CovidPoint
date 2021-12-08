package com.example.covidpoint.presentation.fragments.listcountries

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.DatabaseRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<ListCountriesInterface>() {

    fun getCountriesFromDB() {
        mainRepository.getDataFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCountries(it)
            }, {

            })
    }

    fun getCountryStatistic(id: Int) {
        mainRepository.getDataFromNetworkById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCountryStatistic(it)
            }, {

            })
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}