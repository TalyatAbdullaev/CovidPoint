package com.example.covidpoint.di.modules

import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.network.utils.Urls
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(Urls.BASE_URL)
            .build()
    }

    @Provides
    fun provideApiService (retrofit: Retrofit): CountryApiService =
        retrofit.create(CountryApiService::class.java)
}