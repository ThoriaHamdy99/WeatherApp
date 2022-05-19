package com.example.android.weatherapp.model.remote_source

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.services.SharedPreferencesProvider
import retrofit2.Response

interface RemoteDataSourceInterface {
    suspend fun fetchWeatherData(sharedPref: SharedPreferencesProvider, isFavourite: Boolean): CurrentWeather?
}