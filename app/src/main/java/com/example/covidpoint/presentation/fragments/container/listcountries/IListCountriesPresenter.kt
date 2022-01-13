package com.example.covidpoint.presentation.fragments.container.listcountries

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.presentation.fragments.container.IBasePresenter
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface IListCountriesPresenter: IBasePresenter, MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    override fun showCountries(countries: List<CountryEntity>)

    @StateStrategyType(SkipStrategy::class)
    override fun showCountryStatistic(country: CountryEntity)

    @StateStrategyType(SkipStrategy::class)
    override fun showAlertDialog(message: String, countryId: Int)
}