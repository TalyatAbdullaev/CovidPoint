package com.iwgroup.covidpoint.presentation.fragments.container.listcountries

import com.iwgroup.covidpoint.data.database.CountryEntity
import com.iwgroup.covidpoint.presentation.fragments.container.BaseCountriesInterface
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ListCountriesInterface: BaseCountriesInterface, MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    override fun showCountries(countries: List<CountryEntity>)

    @StateStrategyType(SkipStrategy::class)
    override fun showCountryStatistic(country: CountryEntity)

    @StateStrategyType(SkipStrategy::class)
    override fun showAlertDialog(message: String, countryId: Int)
}