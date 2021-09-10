package com.example.imageoftheday.superview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageoftheday.supermodel.remote.NASAData
import com.example.imageoftheday.supermodel.repository.Repository
import com.example.imageoftheday.supermodel.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val  liveDataObserver : MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
 )  : ViewModel() {

     fun getLiveData() = liveDataObserver

    fun getData(){
        liveDataObserver.postValue(AppState.Loading)
        repository.getServerData(callback)
    }

    private  val callback = object : Callback<NASAData>{
        override fun onResponse(call: Call<NASAData>, response: Response<NASAData>) {
            if (response.isSuccessful && response.body() != null){
                liveDataObserver.value = AppState.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()){
                liveDataObserver.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    liveDataObserver.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<NASAData>, throwable: Throwable) {
            liveDataObserver.value = AppState.Error(throwable)
        }
    }
}