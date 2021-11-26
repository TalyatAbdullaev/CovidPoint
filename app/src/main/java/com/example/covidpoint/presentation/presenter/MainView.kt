package com.example.covidpoint.presentation.presenter

import com.example.covidpoint.data.pojo.Country
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MainView: MvpView {
    fun getCountries(countries: List<Country>)
    fun getCountryStatistic(country: Country)
}