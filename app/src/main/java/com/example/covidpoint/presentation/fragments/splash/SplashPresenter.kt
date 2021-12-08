package com.example.covidpoint.presentation.fragments.splash

import com.example.covidpoint.data.database.mapper.CountryMapper
import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val mainRepository: MainRepository,
    private val mapper: CountryMapper
) : MvpPresenter<SplashInterface>() {

    private val disposable = CompositeDisposable()

    private fun getCountries() {
        disposable.add(mainRepository.getDataFromNetwork()
            .map { it.locations.map { mapper.mapToEntity(it) } }
            .flatMapCompletable { mainRepository.addDataToDB(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.navigateToApp()
            }, {
                viewState.navigateToApp()
            })
        )
    }

    override fun onFirstViewAttach() {
        getCountries()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}