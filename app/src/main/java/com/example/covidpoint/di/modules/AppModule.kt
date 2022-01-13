package com.example.covidpoint.di.modules

import dagger.Module
import android.app.Application
import android.content.Context
import com.example.covidpoint.di.App
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApplication(): App = app

    @Provides
    fun provideContext(): Context = app.applicationContext
}