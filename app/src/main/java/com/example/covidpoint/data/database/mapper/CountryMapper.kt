package com.example.covidpoint.data.database.mapper

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Coordinates
import com.example.covidpoint.data.pojo.Country
import com.example.covidpoint.data.pojo.Statistic

class CountryMapper : Mapper<Country, CountryEntity> {

    override fun mapToEntity(type: Country): CountryEntity =
        CountryEntity(
            type.id,
            type.country,
            type.countryCode,
            type.countryPopulation,
            type.province,
            type.coordinates.latitude.toDouble(),
            type.coordinates.longitude.toDouble(),
            type.latest.confirmed,
            type.latest.deaths,
            type.latest.recovered
        )

    override fun mapFromEntity(type: CountryEntity): Country =
        Country(
            type.id,
            type.country,
            type.countryCode,
            type.countryPopulation,
            type.province,
            Coordinates(type.latitude.toString(), type.longitude.toString()),
            Statistic(type.confirmed, type.deaths, type.recovered),
            null
        )
}