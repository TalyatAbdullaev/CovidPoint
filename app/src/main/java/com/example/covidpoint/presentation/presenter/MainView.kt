package com.example.covidpoint.presentation.presenter

import com.example.covidpoint.data.pojo.Country
import moxy.MvpView

interface MainView: MvpView {
    fun showCountries(countries: List<Country>)

    fun showCountryStatistic(country: Country)
}