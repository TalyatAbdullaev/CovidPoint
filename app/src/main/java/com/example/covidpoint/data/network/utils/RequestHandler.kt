package com.example.covidpoint.data.network.utils

import android.accounts.NetworkErrorException
import android.content.Context
import com.example.covidpoint.R
import retrofit2.HttpException
import java.net.SocketTimeoutException

class RequestHandler(private val context: Context) {

    fun requestHandler(throwable: Throwable): String {
        return when(throwable) {
            is NetworkErrorException -> context.getString(R.string.message_error_connect)
            is HttpException -> context.getString(R.string.message_error_server)
            is SocketTimeoutException -> context.getString(R.string.message_error_server)
            else -> context.getString(R.string.message_error_default_title)
        }
    }
}