package com.iwgroup.covidpoint.presentation.mapper

import com.iwgroup.covidpoint.data.database.CountryEntity
import com.iwgroup.covidpoint.data.pojo.Country

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