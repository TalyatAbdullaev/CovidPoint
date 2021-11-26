package com.example.covidpoint.domen.usecases

import com.example.covidpoint.data.repositories.interfaces.GetCountryStatisticRepository

class GetCountryStatisticUseCase(private val getCountryStatisticRepository: GetCountryStatisticRepository) {
    fun invoke(id: Int) = getCountryStatisticRepository.getCountryStatistic(id)
}