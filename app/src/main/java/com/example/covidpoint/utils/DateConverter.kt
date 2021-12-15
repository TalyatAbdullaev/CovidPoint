package com.example.covidpoint.utils

object DateConverter {
    fun convertDate(date: String): String {
        val dateParts = date.substring(10).split("-")
        return dateParts[2] + " " + getMonthByNumber(dateParts[1].toInt()) + " " + dateParts[0]
    }

    fun getMonthByNumber(num: Int): String? {
        when (num) {
            1 -> return "января"
            2 -> return "февраля"
            3 -> return "марта"
            4 -> return "апреля"
            5 -> return "мая"
            6 -> return "июня"
            7 -> return "июля"
            8 -> return "августа"
            9 -> return "сентября"
            10 -> return "октября"
            11 -> return "ноября"
            12 -> return "декабря"
        }
        return null
    }
}