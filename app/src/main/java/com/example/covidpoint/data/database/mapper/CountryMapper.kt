package com.example.covidpoint.data.database.mapper

interface CountryMapper<E, D> {

    fun mapToEntity(type: E): D

    //    fun mapFromEntity(type: D): E
}