package com.example.covidpoint.presentation.fragments.container.listcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.database.mapper.CountryMapper
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject
import com.example.covidpoint.data.network.utils.Result

class ListCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>
) : MvpPresenter<ListCountriesInterface>() {

    private fun getCountriesFromDB() {
        presenterScope.launch {
            val countries = mainRepository.getDataFromDB()

            withContext(Dispatchers.Main) { viewState.showCountries(countries) }
        }
    }

    fun getCountryStatistic(id: Int) {
        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(id)

            when(response) {
                is Result.Success -> {
                    val country: CountryEntity = mapper.mapToEntity(response.data.location)

                    withContext(Dispatchers.Main) { viewState.showCountryStatistic(country) }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                }
            }
        }
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}