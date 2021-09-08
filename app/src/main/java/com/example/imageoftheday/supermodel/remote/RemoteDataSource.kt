package com.example.imageoftheday.supermodel.remote

import androidx.viewbinding.BuildConfig
import com.example.imageoftheday.BuildConfig.NASA_API_KEY
import com.example.imageoftheday.supermodel.remote.ImageOfTheDayAPI
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val BASE_URL = "https://api.nasa.gov/"
    private val KEY_VALUE = "eJScGbNwjD01kc3zDcNJaDLgEa3ip8dnMykzTlCB"

    private val apiSource = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory
            .create(GsonBuilder().setLenient().create()))
        .build()
        .create(ImageOfTheDayAPI::class.java)

    fun loadImageOfTheDay(callback: Callback<NASAData>){
//        apiSource.getImageOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callback)
        apiSource.getImageOfTheDay(KEY_VALUE).enqueue(callback)
    }
}