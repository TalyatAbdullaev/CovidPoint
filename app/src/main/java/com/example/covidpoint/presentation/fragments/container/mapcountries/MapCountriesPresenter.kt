package com.example.covidpoint.presentation.fragments.container.mapcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.database.mapper.CountryMapper
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class MapCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>
) : MvpPresenter<MapCountriesInterface>() {

    private val compositeDisposable = CompositeDisposable()

    private fun getCountriesFromDB() {
        compositeDisposable.add(
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
        compositeDisposable.add(
            mainRepository.getDataFromNetworkById(id)
                .map { mapper.mapToEntity(it.location) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("TAG", "country - " + it.country)
                    viewState.showCountryStatistic(it)
                }, {
                    Log.d("TAG", "error - " + it.message)
                })
        )
//        presenterScope.launch {
//
//            withContext(Dispatchers.Main){ viewState.showCountries() }
//        }
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}