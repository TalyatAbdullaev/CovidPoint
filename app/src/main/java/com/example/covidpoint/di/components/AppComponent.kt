package com.example.covidpoint.di.components

import com.example.covidpoint.di.modules.*
import com.example.covidpoint.presentation.MainActivity
import com.example.covidpoint.presentation.fragments.container.ContainerFragment
import com.example.covidpoint.presentation.fragments.splash.SplashFragment
import com.example.covidpoint.presentation.fragments.splash.SplashPresenter
import dagger.Component

@Component(
    modules = arrayOf(
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoriesModule::class,
    )
)
interface AppComponent {
    //Activities
    fun inject(activity: MainActivity)

    //Fragments
    fun inject(containerFragment: ContainerFragment)
    fun inject(splashFragment: SplashFragment)

    //Presenters
    fun inject(splashPresenter: SplashPresenter)
}