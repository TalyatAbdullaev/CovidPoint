package com.iwgroup.covidpoint.data.database.typeconverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
object CountryStatsConverter {
    @TypeConverter
    fun fromStringToMap(value: String?): Map<String, Int>? {
        return if(value.isNullOrEmpty())
            null
        else {
            val mapType: Type = object: TypeToken<Map<String, Int>>(){}.type
            Gson().fromJson(value, mapType)
        }
    }

    @TypeConverter
    fun fromMapToString(map: Map<String, Int>?): String? {
        return if(map.isNullOrEmpty())
            null
        else
            Gson().toJson(map)
    }
}