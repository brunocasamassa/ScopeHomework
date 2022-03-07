package com.scope.application.utils

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CustomHttpClient() {

    private lateinit var httpClient: OkHttpClient.Builder


    fun <S> create(
        serviceClass: Class<S>
    ): S {

        setClient()

        return Retrofit.Builder()
            .baseUrl(com.scope.application.BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    private fun setClient() {

        httpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

    }

}