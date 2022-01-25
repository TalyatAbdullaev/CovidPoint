package com.iwgroup.covidpoint.di.modules

import com.iwgroup.covidpoint.data.database.countries.CountriesDao
import com.iwgroup.covidpoint.data.network.services.CountryApiService
import com.iwgroup.covidpoint.data.datasource.DatabaseSourceImpl
import com.iwgroup.covidpoint.data.repositories.MainRepositoryImpl
import com.iwgroup.covidpoint.data.datasource.NetworkSourceImpl
import com.iwgroup.covidpoint.data.datasource.interfaces.DatabaseSource
import com.iwgroup.covidpoint.data.repositories.interfaces.MainRepository
import com.iwgroup.covidpoint.data.datasource.interfaces.NetworkSource
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideDatabaseSource(dao: CountriesDao): DatabaseSource =
        DatabaseSourceImpl(dao)

    @Provides
    fun provideNetworkSource(countryApiService: CountryApiService): NetworkSource =
        NetworkSourceImpl(countryApiService)

    @Provides
    fun provideMainRepository(
        networkSource: NetworkSource,
        databaseSource: DatabaseSource
    ): MainRepository =
        MainRepositoryImpl(networkSource, databaseSource)
}