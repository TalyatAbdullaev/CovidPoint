package com.example.covidpoint.presentation.fragments.container.listcountries

import com.example.covidpoint.data.database.CountryEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.Skip

@StateStrategyType(value = AddToEndStrategy::class)
interface ListCountriesInterface: MvpView {

    fun showCountries(countries: List<CountryEntity>)

    fun showCountryStatistic(country: CountryEntity)

    fun showAlertDialog(message: String)
}