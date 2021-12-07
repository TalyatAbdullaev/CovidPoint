package com.example.covidpoint.presentation.fragments.splash

import com.example.covidpoint.data.repositories.interfaces.MainRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SplashPresenter @Inject constructor(
    private val mainRepository: MainRepository
) : MvpPresenter<SplashInterface>() {
    private val disposable = CompositeDisposable()

    private fun getCountries() {
        mainRepository.getData()
    }

    override fun onFirstViewAttach() {
        getCountries()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}