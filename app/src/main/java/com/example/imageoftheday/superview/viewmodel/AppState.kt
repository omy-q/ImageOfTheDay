package com.example.imageoftheday.superview.viewmodel

import com.example.imageoftheday.supermodel.remote.NASAData

sealed interface AppState{
    data class Success(val serverResponseData: NASAData) : AppState
    data class Error(val error: Throwable) : AppState
    object Loading : AppState
}