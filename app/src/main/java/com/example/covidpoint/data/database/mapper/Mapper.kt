package com.example.covidpoint.data.database.mapper

interface Mapper<E, D> {

    fun mapFromEntity(type: D): E

    fun mapToEntity(type: E): D

}