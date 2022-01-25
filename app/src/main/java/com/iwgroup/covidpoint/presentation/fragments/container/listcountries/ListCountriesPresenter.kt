package com.iwgroup.covidpoint.presentation.fragments.container.listcountries

import android.util.Log
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.network.utils.ExceptionHandler
import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.Country
import com.iwgroup.covidpoint.data.repositories.interfaces.MainRepository
import com.iwgroup.covidpoint.data.mapper.CountryMapper
import kotlinx.coroutines.*
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>,
    private val exceptionHandler: ExceptionHandler
) : MvpPresenter<ListCountriesInterface>() {

    private var countries = listOf<CountryEntity>()
    private val searchingCountries = arrayListOf<CountryEntity>()
    private var job: Job? = null

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    private fun getCountriesFromDB() {
        presenterScope.launch {
            countries = mainRepository.getDataFromDB()
            withContext(Dispatchers.Main) { viewState.showCountries(countries) }
        }
    }

    fun onItemClicked(country: CountryEntity) {
        val countryId = country.id
        getDataFromNetworkById(countryId)
    }

    fun onPositiveButtonClick(countryId: Int) {
        getDataFromNetworkById(countryId)
    }

    fun textChanged(text: String) {
        job?.cancel()
        job = presenterScope.launch {
            delay(300)
            searchingCountries.clear()
            searchingCountries.addAll(countries.filter {
                it.country.startsWith(text, true)
            })

            withContext(Dispatchers.Main) { viewState.showCountries(searchingCountries) }
        }
    }

    private fun getDataFromNetworkById(countryId: Int) {
        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(countryId)

            when (response) {
                is Result.Success -> {
                    val result: CountryEntity = mapper.mapToEntity(response.data.location)
                    mainRepository.addDataToDB(result)
                    countries = mainRepository.getDataFromDB()
                    replaceSearchItem(result)

                    withContext(Dispatchers.Main) { viewState.showCountries(searchingCountries) }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)

                    val message = exceptionHandler.requestHandler(response.throwable)

                    withContext(Dispatchers.Main) { viewState.showAlertDialog(message, countryId) }
                }
            }
        }
    }

    private fun replaceSearchItem(country: CountryEntity) {
        searchingCountries.forEachIndexed let@{ index, countryEntity ->
            if (country.id == countryEntity.id) {
                searchingCountries[index] = country
                return@let
            }
        }
    }
}