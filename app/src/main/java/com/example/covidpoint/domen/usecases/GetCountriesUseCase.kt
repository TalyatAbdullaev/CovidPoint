package com.example.covidpoint.domen.usecases

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.repositories.interfaces.GetCountriesRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val getCountriesRepository: GetCountriesRepository) {
    fun invoke() = getCountriesRepository.getCountries()
}