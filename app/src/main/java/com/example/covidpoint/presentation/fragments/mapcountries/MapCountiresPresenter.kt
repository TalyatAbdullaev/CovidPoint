package com.example.covidpoint.presentation.fragments.mapcountries

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.presentation.fragments.listcountries.ListCountriesPresenter
import com.yandex.mapkit.geometry.Point
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class MapCountiresPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<MapCountriesInterface>() {

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

    fun getCountryByPoint(point: Point) {
        mainRepository.getDataFromDBByCoordinates(point.longitude, point.latitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getCountryStatistic(it.id)
            }, {

            })
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}