package com.iwgroup.covidpoint.presentation.fragments.container.mapcountries

import com.iwgroup.covidpoint.data.database.CountryEntity
import com.iwgroup.covidpoint.presentation.fragments.container.BaseCountriesInterface
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MapCountriesInterface: BaseCountriesInterface, MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    override fun showCountries(countries: List<CountryEntity>)

    @StateStrategyType(SkipStrategy::class)
    override fun showCountryStatistic(country: CountryEntity)

    @StateStrategyType(SkipStrategy::class)
    override fun showAlertDialog(message: String, countryId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showProgressBar()

    @StateStrategyType(SkipStrategy::class)
    fun hideProgressBar()
}