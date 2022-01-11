package com.example.covidpoint.di.modules

import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.datasource.DatabaseSourceImpl
import com.example.covidpoint.data.repositories.MainRepositoryImpl
import com.example.covidpoint.data.datasource.NetworkSourceImpl
import com.example.covidpoint.data.datasource.interfaces.DatabaseSource
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.data.datasource.interfaces.NetworkSource
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideDatabaseRepository(dao: CountriesDao): DatabaseSource =
        DatabaseSourceImpl(dao)

    @Provides
    fun provideNetworkRepository(countryApiService: CountryApiService): NetworkSource =
        NetworkSourceImpl(countryApiService)

    @Provides
    fun provideMainRepository(
        networkSource: NetworkSource,
        databaseSource: DatabaseSource
    ): MainRepository =
        MainRepositoryImpl(networkSource, databaseSource)
}