package com.example.covidpoint.presentation.fragments.mapcountries

import android.util.Log
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class MapCountiresPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<MapCountriesInterface>() {

    private val disposable = CompositeDisposable()

    private fun getCountriesFromDB() {
        disposable.add(
            mainRepository.getDataFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showCountries(it)
                }, {
                    Log.d("TAG", "error - " + it.message)
                })
        )
    }

    fun getCountryStatistic(id: Int) {
        disposable.add(
            mainRepository.getDataFromNetworkById(id)
                .map { it.location }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TAG", "country - " + it.country)
                    viewState.showCountryStatistic(it)
                }, {
                    Log.d("TAG", "error - " + it.message)
                })
        )
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}