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
    @Query("SELECT * FROM countries")
    fun getAllCountries(): Single<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<CountryEntity>): Completable

    @Query("DELETE FROM countries")
    fun deleteAllCountries()

    @Query("SELECT * FROM countries WHERE longitude == :longitude and latitude == :latitude")
    fun getCountryByCoordinates(longitude: Double, latitude: Double): Single<CountryEntity>
}