package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather

interface LocalDataSourceInterface {
    suspend fun insert(currentWeather: CurrentWeather?)
    fun getWeather(): LiveData<CurrentWeather>?
    suspend fun deleteWeather()
    suspend fun deleteAll()
}