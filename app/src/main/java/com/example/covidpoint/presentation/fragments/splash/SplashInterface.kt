package com.example.covidpoint.presentation.fragments.splash

import com.example.covidpoint.data.pojo.Country
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface SplashInterface: MvpView {
    fun navigateToApp()
}