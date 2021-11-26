package com.example.covidpoint.di

import android.app.Application
import com.example.covidpoint.di.components.AppComponent
import com.example.covidpoint.di.components.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.create()
    }
}