package com.example.covidpoint.presentation.fragments.container.mapcountries

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.presentation.fragments.container.listcountries.ListCountriesInterface
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MapCountriesInterface : MvpView, ListCountriesInterface {

    override fun showCountries(countries: List<CountryEntity>)

    override fun showCountryStatistic(country: CountryEntity)

    override fun showAlertDialog(message: String)
}