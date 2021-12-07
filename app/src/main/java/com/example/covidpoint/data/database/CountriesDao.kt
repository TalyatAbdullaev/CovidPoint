package com.example.covidpoint.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.covidpoint.data.pojo.Country
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CountriesDao {
    @Query("SELECT * FROM countries")
    fun getAllCountries(): Single<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<Country>): Completable

    @Query("DELETE FROM countries")
    fun deleteAllCountries()
}