package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather

interface LocalDataSourceInterface {
    suspend fun insert(currentWeather: CurrentWeather?)
    fun getWeather(lat: String?, lon: String?): LiveData<CurrentWeather>?
    suspend fun deleteWeather(lat:String , lng:String)
    suspend fun deleteAll()
}