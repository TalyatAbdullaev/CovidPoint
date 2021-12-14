package com.example.covidpoint.presentation.fragments.mapcountries

import android.util.Log
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.yandex.mapkit.geometry.Point
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class MapCountiresPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<MapCountriesInterface>() {

    private val disposable = CompositeDisposable()

    fun getCountriesFromDB() {
        disposable.add(
            mainRepository.getDataFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showCountries(it)
                }, {

                })
        )
    }

    fun getCountryStatistic(id: Int) {
        disposable.add(
            mainRepository.getDataFromNetworkById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showCountryStatistic(it)
                }, {

                })
        )
    }

    fun getCountryByPoint(point: Point) {
        disposable.add(mainRepository.getDataFromDBByCoordinates(point.longitude, point.latitude)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TAG", it.toString())
                getCountryStatistic(it.id)
            }, {

            })
        )
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}