package com.iwgroup.covidpoint.di.modules

import dagger.Module
import android.content.Context
import com.iwgroup.covidpoint.di.App
import dagger.Provides

@Module
class AppModule(private val app: App) {

    @Provides
    fun provideApplication(): App = app

    @Provides
    fun provideContext(): Context = app.applicationContext
}