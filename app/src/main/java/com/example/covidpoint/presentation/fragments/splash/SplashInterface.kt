package com.example.covidpoint.presentation.fragments.splash

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface SplashInterface: MvpView {
    fun navigateToApp()
}