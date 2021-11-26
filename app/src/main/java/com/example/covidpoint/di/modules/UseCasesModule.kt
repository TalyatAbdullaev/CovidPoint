package com.example.covidpoint.di.modules

import com.example.covidpoint.data.repositories.interfaces.GetCountriesRepository
import com.example.covidpoint.data.repositories.interfaces.GetCountryStatisticRepository
import com.example.covidpoint.domen.usecases.GetCountriesUseCase
import com.example.covidpoint.domen.usecases.GetCountryStatisticUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun provideGetCountriesUseCase(getCountriesRepository: GetCountriesRepository): GetCountriesUseCase =
        GetCountriesUseCase(getCountriesRepository)

    @Provides
    fun provideGetCountryStatisticUseCase(getCountryStatisticRepository: GetCountryStatisticRepository): GetCountryStatisticUseCase =
        GetCountryStatisticUseCase(getCountryStatisticRepository)
}