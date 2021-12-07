package com.example.covidpoint.di.modules

import android.content.Context
import androidx.room.Room
import com.example.covidpoint.data.database.CountriesDao
import com.example.covidpoint.data.database.CountriesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideCountriesDatabase(context: Context): CountriesDatabase =
        Room.databaseBuilder(
            context,
            CountriesDatabase::class.java,
            "countries_database"
        ).build()

    @Provides
    fun provideCountriesDao(db: CountriesDatabase): CountriesDao = db.countriesDao()
}