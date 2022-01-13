package com.example.covidpoint.presentation.fragments.container

import com.example.covidpoint.data.database.CountryEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IBasePresenter: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCountries(countries: List<CountryEntity>)

    @StateStrategyType(SkipStrategy::class)
    fun showCountryStatistic(country: CountryEntity)

    @StateStrategyType(SkipStrategy::class)
    fun showAlertDialog(message: String, countryId: Int)
}