package com.example.covidpoint.di.modules

import androidx.room.Room
import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.database.CountriesDatabase
import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.database.CountriesDatabaseConstants
import com.example.covidpoint.presentation.mapper.CountryMapper
import com.example.covidpoint.presentation.mapper.CountryMapperImpl
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.di.App
import dagger.Module
import dagger.Provides
import org.kodein.di.*

@Module
class DatabaseModule {

    @Provides
    fun provideCountriesDatabase(app: App): CountriesDatabase =
        Room.databaseBuilder(
            app,
            CountriesDatabase::class.java,
            CountriesDatabaseConstants.COUNTRIES_DATABASE_NAME
        ).build()

    @Provides
    fun provideCountriesDao(db: CountriesDatabase): CountriesDao = db.countriesDao()

    @Provides
    fun provideMapper(): CountryMapper<Country, CountryEntity> = CountryMapperImpl()
}