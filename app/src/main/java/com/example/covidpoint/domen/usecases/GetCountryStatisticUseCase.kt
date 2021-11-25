package com.example.covidpoint.domen.usecases

import com.example.covidpoint.data.repositories.GetCountryStatisticRepositoryImpl

class GetCountryStatisticUseCase(private val getCountryStatisticRepositoryImpl: GetCountryStatisticRepositoryImpl) {
    fun invoke(id: Int) = getCountryStatisticRepositoryImpl.getCountryStatistic(id)
}