package com.example.covidpoint.di.modules

import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.repositories.GetCountriesRepositoryImpl
import com.example.covidpoint.data.repositories.GetCountryStatisticRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.GetCountriesRepository
import com.example.covidpoint.data.repositories.interfaces.GetCountryStatisticRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideGetCountriesRepository(apiService: ApiService): GetCountriesRepository =
        GetCountriesRepositoryImpl(apiService)

    @Provides
    fun provideGetCountryStatisticRepository(apiService: ApiService): GetCountryStatisticRepository =
        GetCountryStatisticRepositoryImpl(apiService)
}