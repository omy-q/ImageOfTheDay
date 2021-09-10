package com.example.imageoftheday.supermodel.repository

import com.example.imageoftheday.supermodel.remote.NASAData
import retrofit2.Callback

interface Repository {

    fun getServerData(callback: Callback<NASAData>)
}