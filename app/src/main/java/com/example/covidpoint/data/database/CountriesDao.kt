package com.example.covidpoint.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covidpoint.data.pojo.Country
import com.yandex.mapkit.geometry.Point
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CountriesDao {
    @Query("SELECT * FROM ${DatabaseConstants.COUNTRIES_TABLE_NAME}")
    suspend fun getAllCountries(): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()
}