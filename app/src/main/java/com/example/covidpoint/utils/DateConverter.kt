package com.example.covidpoint.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object DateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDate(date: String): String {
        val localDate = OffsetDateTime.parse(date).toLocalDate()

        return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
            .withLocale(Locale("ru"))
            .format(localDate)
    }
}