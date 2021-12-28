package com.example.covidpoint.di.modules

import com.example.covidpoint.data.network.services.CountryApiService
import com.example.covidpoint.data.network.utils.Urls
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Urls.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService (retrofit: Retrofit): CountryApiService =
        retrofit.create(CountryApiService::class.java)
}