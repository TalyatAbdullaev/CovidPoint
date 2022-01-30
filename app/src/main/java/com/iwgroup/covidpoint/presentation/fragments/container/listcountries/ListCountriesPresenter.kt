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

    private val countries = arrayListOf<CountryEntity>()
    private val searchedCountries = arrayListOf<CountryEntity>()
    private var job: Job? = null

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    private fun getCountriesFromDB() {
        presenterScope.launch {
            val countriesFromDB = mainRepository.getDataFromDB()
            countries.addAll(countriesFromDB)
            searchedCountries.addAll(countriesFromDB)

            withContext(Dispatchers.Main) { viewState.showCountries(countries.toList()) }
        }
    }

    fun onItemClicked(country: CountryEntity) {
        if (checkItemPreviouslyClicked(country))
            viewState.showCountries(searchedCountries.toList())
        else
            getDataFromNetworkById(country.id)
    }

    fun onPositiveButtonClick(countryId: Int) {
        getDataFromNetworkById(countryId)
    }

    fun textChanged(text: String) {
        job?.cancel()
        job = presenterScope.launch {
            delay(300)
            searchedCountries.clear()
            searchedCountries.addAll(countries.filter {
                it.country.startsWith(text, true)
            })

            Log.d("TAG", "Countries - " + countries.toString())

            withContext(Dispatchers.Main) { viewState.showCountries(searchedCountries.toList()) }
        }
    }

    private fun getDataFromNetworkById(countryId: Int) {
        Log.d("TAG", "countryID - " + countryId)
        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(countryId)

            when (response) {
                is Result.Success -> {
                    val result: CountryEntity = mapper.mapToEntity(response.data.location)
                    replaceClickedItem(countries, result)
                    replaceClickedItem(searchedCountries, result)

                    withContext(Dispatchers.Main) { viewState.showCountries(searchedCountries.toList()) }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                    val message = exceptionHandler.requestHandler(response.throwable)

                    withContext(Dispatchers.Main) { viewState.showAlertDialog(message, countryId) }
                }
            }
        }
    }

    private fun replaceClickedItem(list: ArrayList<CountryEntity>, country: CountryEntity) {
        list.forEachIndexed let@{ index, countryEntity ->
            if (country.id == countryEntity.id) {
                list[index] = country
                return@let
            }
        }
    }

    private fun checkItemPreviouslyClicked(country: CountryEntity): Boolean {
        var checked: Boolean = false
        countries.forEach let@{
            if (it.id == country.id) {
                if (it.confirmedStats != null) {
                    checked = true
                }
                return@let
            }
        }
        return checked
    }
}