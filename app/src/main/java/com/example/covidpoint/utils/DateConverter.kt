package com.example.covidpoint.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun convertDate(date: String): String {

        val localDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        val outDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        return outDateFormat.format(localDate)
    }
}