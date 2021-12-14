package com.example.covidpoint.presentation.fragments.listcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.DatabaseRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<ListCountriesInterface>() {

    private val disposable = CompositeDisposable()

    fun getCountriesFromDB() {
        disposable.add(mainRepository.getDataFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("TAG", "countries from db - " + it.toString())

                viewState.showCountries(it)
            }, {

            })
        )
    }

    fun getCountryStatistic(id: Int) {
        disposable.add(mainRepository.getDataFromNetworkById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCountryStatistic(it)
            }, {

            })
        )
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}