package com.example.covidpoint.presentation.mapper

interface CountryMapper<E, D> {

    fun mapToEntity(type: E): D

    //    fun mapFromEntity(type: D): E
}