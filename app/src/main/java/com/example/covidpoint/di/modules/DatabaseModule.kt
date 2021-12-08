package com.example.covidpoint.di.modules

import androidx.room.Room
import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.database.CountriesDatabase
import com.example.covidpoint.data.database.mapper.CountryMapper
import com.example.covidpoint.di.App
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideCountriesDatabase(app: App): CountriesDatabase =
        Room.databaseBuilder(
            app,
            CountriesDatabase::class.java,
            "countries_database"
        )
            //.allowMainThreadQueries()
            .build()

    @Provides
    fun provideCountriesDao(db: CountriesDatabase): CountriesDao = db.countriesDao()

    @Provides
    fun provideMapper(): CountryMapper = CountryMapper()
}