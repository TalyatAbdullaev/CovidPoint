package com.example.covidpoint.presentation.mapper

import com.example.covidpoint.data.database.CountryEntity
import com.example.covidpoint.data.pojo.Country

class CountryMapperImpl : CountryMapper<Country, CountryEntity> {

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
            type.latest.recovered,
            type.timelines?.confirmed?.timeline
        )
}