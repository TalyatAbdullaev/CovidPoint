package com.iwgroup.covidpoint.data.database.countries

import androidx.room.*

@Dao
interface CountriesDao {
    @Query("SELECT * FROM ${CountriesDatabaseConstants.COUNTRIES_TABLE_NAME}")
    suspend fun getAllCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)

    @Query("DELETE FROM ${CountriesDatabaseConstants.COUNTRIES_TABLE_NAME}")
    suspend fun deleteAllCountries()
}