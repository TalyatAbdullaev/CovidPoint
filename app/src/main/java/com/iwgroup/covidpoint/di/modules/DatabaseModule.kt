package com.iwgroup.covidpoint.di.modules

import androidx.room.Room
import com.iwgroup.covidpoint.data.database.countries.CountriesDao
import com.iwgroup.covidpoint.data.database.countries.CountriesDatabase
import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.database.countries.CountriesDatabaseConstants
import com.iwgroup.covidpoint.data.mapper.CountryMapper
import com.iwgroup.covidpoint.data.mapper.CountryMapperImpl
import com.iwgroup.covidpoint.data.pojo.Country
import com.iwgroup.covidpoint.di.App
import dagger.Module
import dagger.Provides

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