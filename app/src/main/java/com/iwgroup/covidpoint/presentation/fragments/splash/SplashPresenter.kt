package com.iwgroup.covidpoint.presentation.fragments.splash

import android.util.Log
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.mapper.CountryMapper
import com.iwgroup.covidpoint.data.network.utils.Result
import com.iwgroup.covidpoint.data.pojo.Country
import com.iwgroup.covidpoint.data.repositories.interfaces.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper<Country, CountryEntity>
) : MvpPresenter<SplashInterface>() {

    override fun onFirstViewAttach() {
        getCountriesFromNetwork()
    }

    private fun getCountriesFromNetwork() {
        presenterScope.launch {
            val response = mainRepository.getDataFromNetwork()

            when (response) {
                is Result.Success -> {
                    val countries: List<CountryEntity> = response.data.locations.mapNotNull {
                        if (it.coordinates.latitude.isNotEmpty() && it.coordinates.longitude.isNotEmpty()) {
                            mapper.mapToEntity(it)
                        } else
                            null
                    }
                    mainRepository.addDataToDB(countries)
                }
                is Result.Error -> {
                    Log.d("TAG", "error - " + response.throwable.message)
                }
            }
            withContext(Dispatchers.Main) { viewState.navigateToApp() }
        }
    }
}