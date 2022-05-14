package com.example.android.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.services.SharedPreferencesProvider

interface RepositoryInterface {
    suspend fun insert(currentWeather: CurrentWeather?)
    suspend fun getWeather(lat: String?, lon: String?): LiveData<CurrentWeather>?
    suspend fun deleteWeather(lat:String , lng:String)
    suspend fun deleteAll()
    suspend fun fetchWeatherData(sharedPref: SharedPreferencesProvider): CurrentWeather?
}
