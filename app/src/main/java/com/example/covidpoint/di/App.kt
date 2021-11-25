package com.example.covidpoint.di

import android.app.Application
import com.example.covidpoint.di.components.AppComponent

class App : Application() {
    companion object {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.create()
    }
}