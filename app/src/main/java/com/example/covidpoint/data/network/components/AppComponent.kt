package com.example.covidpoint.data.network.components

import com.example.covidpoint.data.network.NetworkUtils
import com.example.covidpoint.data.network.modules.NetworkModule
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun getNetworkUtils(): NetworkUtils
}