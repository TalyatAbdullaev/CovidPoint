package com.example.covidpoint.presentation.presenter

import com.example.covidpoint.domen.usecases.GetCountriesUseCase
import com.example.covidpoint.domen.usecases.GetCountryStatisticUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class Presenter(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getCountryStatisticUseCase: GetCountryStatisticUseCase
) : MvpPresenter<MainView>() {
    private val disposable = CompositeDisposable()

    fun getCountries() {
        disposable.add(
            getCountriesUseCase.invoke()
                .subscribeOn(Schedulers.io())
                .map { it.locations }
                .subscribe(
                    {
                        viewState.showCountries(it)
                    }, {

                    })
        )
    }

    fun getCountryStatistic(id: Int) {
        disposable.add(
            getCountryStatisticUseCase.invoke(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        viewState.showCountryStatistic(it)
                    }, {

                    }
                ))

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}