package com.example.imageoftheday.supermodel.remote


import com.example.imageoftheday.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val BASE_URL = "https://api.nasa.gov/"

    private val apiSource = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory
            .create(GsonBuilder().setLenient().create()))
        .build()
        .create(ImageOfTheDayAPI::class.java)

    fun loadImageOfTheDay(callback: Callback<NASAData>){
        apiSource.getImageOfTheDay(BuildConfig.NASA_API_KEY).enqueue(callback)
    }
}