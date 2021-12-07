package com.example.covidpoint.presentation.fragments.listcountries

import com.example.covidpoint.data.pojo.Country
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ListCountriesInterface: MvpView {

    fun showCountries(countries: List<Country>)
}