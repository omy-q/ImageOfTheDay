package com.example.imageoftheday.supermodel.repository

import com.example.imageoftheday.supermodel.remote.NASAData
import com.example.imageoftheday.supermodel.remote.RemoteDataSource
import retrofit2.Callback

class RepositoryImpl : Repository {
    private val remoteDataSource = RemoteDataSource()
    override fun getServerData(callback: Callback<NASAData>) {
        remoteDataSource.loadImageOfTheDay(callback)
    }
}