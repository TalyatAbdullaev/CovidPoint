package com.example.covidpoint.networkutils.modules

import dagger.Module
import com.example.covidpoint.networkutils.NetworkUtils
import dagger.Provides


@Module
class NetworkModule {

    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}