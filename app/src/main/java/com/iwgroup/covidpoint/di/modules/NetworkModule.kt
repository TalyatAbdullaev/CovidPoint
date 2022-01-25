package com.iwgroup.covidpoint.di.modules

import android.content.Context
import com.iwgroup.covidpoint.data.network.services.CountryApiService
import com.iwgroup.covidpoint.data.network.utils.ExceptionHandler
import com.iwgroup.covidpoint.data.network.utils.Urls
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    @Provides
    fun provideRequestHandler(context: Context): ExceptionHandler =
        ExceptionHandler(context)
}