package com.iwgroup.covidpoint.data.mapper

import com.iwgroup.covidpoint.data.database.countries.CountryEntity
import com.iwgroup.covidpoint.data.pojo.Country

class CountryMapperImpl : CountryMapper<Country, CountryEntity> {

    override fun mapToEntity(type: Country): CountryEntity {
        var stats: Map<String, Int>? = null
        if (type.timelines != null) {
            stats = type.timelines.confirmed?.timeline
        }

        return CountryEntity(
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
            stats
        )
    }
}