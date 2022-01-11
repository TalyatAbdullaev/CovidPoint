package com.example.covidpoint.presentation.fragments.container.mapcountries

import android.util.Log
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.presentation.mapper.CountryMapper
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject
import com.example.covidpoint.data.network.utils.Result
import com.example.covidpoint.utils.AppUtils
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.UserData

class MapCountriesPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>
) : MvpPresenter<MapCountriesInterface>() {

    private fun getCountriesFromDB() {
        presenterScope.launch {
            val countries = mainRepository.getDataFromDB()
            withContext(Dispatchers.Main) { viewState.showCountries(countries) }
        }
    }

    fun onPlacemarkTap(mapObject: MapObject) {
        val userData = mapObject.userData as UserData
        val countryId = userData.data.getValue(AppUtils.ID_KEY).toInt()

        presenterScope.launch {
            val response = mainRepository.getDataFromNetworkById(countryId)

            when(response) {
                is Result.Success -> {
                    val result: CountryEntity = mapper.mapToEntity(response.data.location)
                    withContext(Dispatchers.Main) { viewState.showCountryStatistic(result) }
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                    viewState.showAlertDialog(response.throwable.message.toString())
                }
            }
        }
    }

    override fun onFirstViewAttach() {
        getCountriesFromDB()
    }
}