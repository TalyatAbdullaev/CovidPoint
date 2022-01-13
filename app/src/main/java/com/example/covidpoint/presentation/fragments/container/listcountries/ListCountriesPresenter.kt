package com.example.covidpoint.presentation.fragments.container.listcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.network.utils.RequestHandler
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.presentation.mapper.CountryMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class ListCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>,
    private val requestHandler: RequestHandler
) : MvpPresenter<IListCountriesPresenter>() {

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    private fun getCountriesFromDB() {
        presenterScope.launch {
            val countries = mainRepository.getDataFromDB()
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

    private fun getDataFromNetworkById(countryId: Int) {
        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(countryId)

            when(response) {
                is Result.Success -> {
                    val result: CountryEntity = mapper.mapToEntity(response.data.location)
                    withContext(Dispatchers.Main) { viewState.showCountryStatistic(result) }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                    val message = requestHandler.requestHandler(response.throwable)
                    viewState.showAlertDialog(message, countryId)
                }
            }
        }
    }
}