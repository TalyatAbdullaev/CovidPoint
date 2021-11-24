package com.example.covidpoint.data.network.modules

import dagger.Module
import com.example.covidpoint.data.network.NetworkUtils
import dagger.Provides


@Module
class NetworkModule {

    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}