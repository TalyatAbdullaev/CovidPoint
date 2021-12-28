package com.example.covidpoint.di

import android.app.Application
import com.example.covidpoint.data.network.utils.ApiKeys
import com.example.covidpoint.di.components.AppComponent
import com.example.covidpoint.di.components.DaggerAppComponent
import com.example.covidpoint.di.modules.AppModule
import com.example.covidpoint.utils.AppUtils
import com.yandex.mapkit.MapKitFactory

class App : Application() {
    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(ApiKeys.YANDEX_API_KEY)

        graph = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}