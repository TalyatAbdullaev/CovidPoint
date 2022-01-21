package com.iwgroup.covidpoint.di

import android.app.Application
import com.iwgroup.covidpoint.data.network.utils.ApiKeys
import com.iwgroup.covidpoint.di.components.AppComponent
import com.iwgroup.covidpoint.di.components.DaggerAppComponent
import com.iwgroup.covidpoint.di.modules.AppModule
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