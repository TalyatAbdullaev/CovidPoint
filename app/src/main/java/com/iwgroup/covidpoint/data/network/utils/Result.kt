package com.iwgroup.covidpoint.data.network.utils

sealed class Result<out T> {
    data class Success<out R>(val data: R) : Result<R>()
    data class Error(val throwable: Throwable) : Result<Nothing>()
}
