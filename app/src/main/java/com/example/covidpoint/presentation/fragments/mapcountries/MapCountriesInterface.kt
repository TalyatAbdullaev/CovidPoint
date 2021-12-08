package com.example.covidpoint.presentation.fragments.mapcountries

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.presentation.fragments.listcountries.ListCountriesInterface
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface MapCountriesInterface: MvpView, ListCountriesInterface {
    override fun showCountries(countries: List<CountryEntity>)

    override fun showCountryStatistic(country: Country)
}