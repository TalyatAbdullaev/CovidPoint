package com.example.covidpoint.networkutils.components

import com.example.covidpoint.networkutils.NetworkUtils
import com.example.covidpoint.networkutils.modules.NetworkModule
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getNetworkUtils(): NetworkUtils
}