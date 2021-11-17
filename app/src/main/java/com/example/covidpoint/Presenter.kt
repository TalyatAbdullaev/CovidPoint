package com.example.covidpoint

import android.util.Log
import com.example.covidpoint.api.ApiFactory
import io.reactivex.rxjava3.schedulers.Schedulers

class Presenter {

    fun getCountries() {
        ApiFactory.apiService.getCountries()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    Log.d("TAG", it.toString())
                },
                {
                    Log.d("TAG", "error " + it.message)
                })
    }

    fun getCountryStatistic(id: Int) {
        ApiFactory.apiService.getCountryStatistics(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {

                }, {

                }
            )

    }
}