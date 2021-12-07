package com.example.covidpoint.presentation.fragments.listcountries

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.DatabaseRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<ListCountriesInterface>() {

    private var countries: List<Country> = listOf()

    fun getCountriesFromDB() {
        mainRepository.getDataFromDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countries = it
                showData()
            }, {

            })
    }

    fun getCountryStatistic(id: Int) {
        mainRepository.
    }

    private fun showData() {
        viewState.showCountries(countries)
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}