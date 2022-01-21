package com.iwgroup.covidpoint.di.components

import com.iwgroup.covidpoint.di.modules.*
import com.iwgroup.covidpoint.presentation.MainActivity
import com.iwgroup.covidpoint.presentation.fragments.container.ContainerFragment
import com.iwgroup.covidpoint.presentation.fragments.container.listcountries.ListCountriesFragment
import com.iwgroup.covidpoint.presentation.fragments.container.mapcountries.MapCountriesFragment
import com.iwgroup.covidpoint.presentation.fragments.splash.SplashFragment
import dagger.Component

@Component(
    modules = arrayOf(
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoriesModule::class
    )
)
interface AppComponent {
    //Activities
    fun inject(activity: MainActivity)

    //Fragments
    fun inject(containerFragment: ContainerFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(listCountriesFragment: ListCountriesFragment)
    fun inject(mapCountriesFragment: MapCountriesFragment)
}