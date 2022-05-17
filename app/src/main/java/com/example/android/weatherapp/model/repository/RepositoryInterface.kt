package com.example.android.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.services.SharedPreferencesProvider

interface RepositoryInterface {
    suspend fun insert(currentWeather: CurrentWeather?)
    suspend fun getWeather(): LiveData<CurrentWeather>?
    suspend fun deleteWeather()
    suspend fun deleteAll()
    suspend fun fetchWeatherData(): CurrentWeather?
    //suspend fun getWeatherData(): CurrentWeather?
}
