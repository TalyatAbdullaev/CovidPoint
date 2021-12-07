package com.example.covidpoint.presentation.fragments.listcountries

import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.DatabaseRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import moxy.MvpPresenter
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(private val mainRepository: MainRepository) :
    MvpPresenter<ListCountriesInterface>() {

    private val countries: List<Country> = mainRepository.getDataFromDB()

        fun showData() {
            viewState.showCountries(countries)
        }

    override fun onFirstViewAttach() {
        showData()
    }
}