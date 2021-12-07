package com.example.covidpoint.di.modules

import dagger.Module
import android.app.Application
import android.content.Context
import dagger.Provides

import javax.inject.Singleton




@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplication():Application = application

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}