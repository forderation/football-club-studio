package com.forderation.footballclubstudio.api

import com.forderation.footballclubstudio.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient{
    private val http = OkHttpClient().newBuilder()
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(10,TimeUnit.SECONDS)
        .writeTimeout(10,TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_API)
        .client(http)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service : Endpoints = retrofit.create(Endpoints::class.java)
}