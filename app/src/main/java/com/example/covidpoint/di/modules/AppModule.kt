package com.example.covidpoint.di.modules

import dagger.Module
import android.app.Application
import com.example.covidpoint.presentation.presenter.Presenter
import dagger.Provides
import javax.inject.Inject

import javax.inject.Singleton




@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication():Application = application
}