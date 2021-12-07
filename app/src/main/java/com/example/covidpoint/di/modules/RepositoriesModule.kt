package com.example.covidpoint.di.modules

import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.network.api.ApiService
import com.example.covidpoint.data.repositories.DatabaseRepositoryImpl
import com.example.covidpoint.data.repositories.MainRepositoryImpl
import com.example.covidpoint.data.repositories.NetworkRepositoryImpl
import com.example.covidpoint.data.repositories.interfaces.DatabaseRepository
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import com.example.covidpoint.data.repositories.interfaces.NetworkRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideDatabaseRepository(dao: CountriesDao): DatabaseRepository =
        DatabaseRepositoryImpl(dao)

    @Provides
    fun provideNetworkRepository(apiService: ApiService): NetworkRepository =
        NetworkRepositoryImpl(apiService)

    @Provides
    fun provideMainRepository(
        networkRepository: NetworkRepository,
        databaseRepository: DatabaseRepository
    ): MainRepository =
        MainRepositoryImpl(networkRepository, databaseRepository)
}