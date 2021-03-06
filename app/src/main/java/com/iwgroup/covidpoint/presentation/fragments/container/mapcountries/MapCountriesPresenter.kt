package com.iwgroup.covidpoint.presentation.fragments.container.mapcountries

import android.util.Log
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.mapper.CountryMapper
import com.iwgroup.covidpoint.data.network.utils.ExceptionHandler
import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.Country
import com.iwgroup.covidpoint.data.repositories.interfaces.MainRepository
import com.iwgroup.covidpoint.utils.AppUtils
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class MapCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>,
    private val exceptionHandler: ExceptionHandler
) : MvpPresenter<MapCountriesInterface>() {

    private val searchedCountries = arrayListOf<CountryEntity>()

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }

    private fun getCountriesFromDB() {
        presenterScope.launch {
            val countries = mainRepository.getDataFromDB()
            withContext(Dispatchers.Main) { viewState.showCountries(countries) }
        }
    }

    fun onPlacemarkTap(mapObject: MapObject) {
        val userData = mapObject.userData as UserData
        val countryId = userData.data.getValue(AppUtils.ID_KEY).toInt()
        val country = checkPreviouslySearched(countryId)

        if(country != null) viewState.showCountryStatistic(country)
        else getDataFromNetworkById(countryId)
    }

    fun onPositiveButtonClick(countryId: Int) {
        getDataFromNetworkById(countryId)
    }

    private fun checkPreviouslySearched(id: Int): CountryEntity? {
        var country: CountryEntity? = null

        searchedCountries.forEach let@ {
            if (it.id == id) {
                country = it
                return@let
            }
        }
        return country
    }

    private fun getDataFromNetworkById(countryId: Int) {
        viewState.showProgressBar()
        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(countryId)

            when (response) {
                is Result.Success -> {
                    val result: CountryEntity = mapper.mapToEntity(response.data.location)
                    searchedCountries.add(result)

                    withContext(Dispatchers.Main) {
                        viewState.hideProgressBar()
                        viewState.showCountryStatistic(result)
                    }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                    val message = exceptionHandler.requestHandler(response.throwable)

                    withContext(Dispatchers.Main) {
                        viewState.hideProgressBar()
                        viewState.showAlertDialog(message, countryId)
                    }
                }
            }
        }
    }
}