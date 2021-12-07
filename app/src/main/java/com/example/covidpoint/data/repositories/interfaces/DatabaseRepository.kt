package com.example.covidpoint.data.repositories.interfaces

import com.example.covidpoint.data.pojo.Country
import io.reactivex.rxjava3.core.Single

interface DatabaseRepository {

    fun insertCountries(countries: List<Country>)

    fun getCountries(): List<Country>
}