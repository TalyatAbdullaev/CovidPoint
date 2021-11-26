package com.example.covidpoint.presentation.fragments.interfaces

import com.example.covidpoint.data.pojo.Country

interface ShowCountries {
    fun showListCountries(countries: List<Country>)

    fun showCountryStatistic(country: Country)
}