package com.example.covidpoint.presentation.presenter

import android.util.Log
import com.example.covidpoint.domen.usecases.GetCountriesUseCase
import com.example.covidpoint.domen.usecases.GetCountryStatisticUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

class Presenter @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryStatisticUseCase: GetCountryStatisticUseCase
) : MvpPresenter<MainView>() {
    private val disposable = CompositeDisposable()

    private fun getCountries() {
        disposable.add(
            getCountriesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.locations }
                .subscribe(
                    {
                        viewState.getCountries(it)
                        Log.d("TAG", it.toString())
                    }, {
                        Log.d("TAG", it.message.toString())
                    })
        )
    }

    private fun getCountryStatistic(id: Int) {
        disposable.add(
            getCountryStatisticUseCase.invoke(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        viewState.getCountryStatistic(it)
                    }, {

                    }
                ))
    }

    override fun onFirstViewAttach() {
        getCountries()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}