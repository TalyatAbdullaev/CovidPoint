package com.example.covidpoint.presentation.fragments.container.listcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.database.mapper.CountryMapper
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>
) : MvpPresenter<ListCountriesInterface>() {

    private val disposable = CompositeDisposable()

    private fun getCountriesFromDB() {
        disposable.add(
            mainRepository.getDataFromDB()
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
            .map { it.location }
            .map { mapper.mapToEntity(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.showCountryStatistic(it)
            }, {
                Log.d("TAG", "error - " + it.message)
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