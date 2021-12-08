package com.example.covidpoint.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.covidpoint.data.pojo.Country

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class CountriesDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao
}