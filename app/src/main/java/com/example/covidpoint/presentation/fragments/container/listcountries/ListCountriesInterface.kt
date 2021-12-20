package com.example.covidpoint.presentation.fragments.container.listcountries

import com.example.covidpoint.data.database.CountryEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ListCountriesInterface: MvpView {

    fun showCountries(countries: List<CountryEntity>)

    fun showCountryStatistic(country: CountryEntity)
}