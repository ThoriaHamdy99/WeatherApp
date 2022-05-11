package com.example.android.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.weather_models.CurrentWeather

interface RepositoryInterface {
    suspend fun getWeather(lat: String, lon: String, units: String, lang: String): CurrentWeather
    fun insert(weather: CurrentWeather)
    val getWeather: LiveData<CurrentWeather>
}
