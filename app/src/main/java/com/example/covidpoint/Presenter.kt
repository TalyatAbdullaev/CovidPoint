package com.example.covidpoint

import android.util.Log
import com.example.covidpoint.api.ApiFactory
import com.example.covidpoint.pojo.Country
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import moxy.MvpView

class Presenter : MvpPresenter<MvpView>() {
    val disposable = CompositeDisposable()


    val countries = arrayListOf<Country>()

    fun getCountries() {
        disposable.add(
            ApiFactory.apiService.getCountries()
            .subscribeOn(Schedulers.io())
            .map { it.locations }
            .subscribe(
                {
                    Log.d("TAG", it.toString())
                    countries.addAll(it)
                },
                {
                    Log.d("TAG", "error " + it.message)
                })
        )
    }

    fun getCountryStatistic(id: Int) {
        disposable.add(
            ApiFactory.apiService.getCountryStatistics(id)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {

                }, {

                }
            ))

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}