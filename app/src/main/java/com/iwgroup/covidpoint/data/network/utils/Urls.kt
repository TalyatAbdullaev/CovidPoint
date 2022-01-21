package com.iwgroup.covidpoint.data.network.utils

object Urls {
    const val BASE_URL = "https://coronavirus-tracker-api.herokuapp.com/"
    const val COUNTRY_FLAG_URL = "https://flagcdn.com/120x90/%s.png"

    const val GET_COUNTRIES_ENDPOINT = "v2/locations"
    const val GET_COUNTRY_BY_ID_ENDPOINT = "v2/locations/{${RequestField.ID}}"
}