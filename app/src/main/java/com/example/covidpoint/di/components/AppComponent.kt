package com.example.covidpoint.di.components

import com.example.covidpoint.di.modules.AppModule
import com.example.covidpoint.di.modules.NetworkModule
import com.example.covidpoint.presentation.fragments.BaseFragment
import com.example.covidpoint.presentation.presenter.Presenter
import dagger.Component

@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {
    //Fragments
    fun inject(baseFragment: BaseFragment)

    //Presenters
    fun inject(presenter: Presenter)
}