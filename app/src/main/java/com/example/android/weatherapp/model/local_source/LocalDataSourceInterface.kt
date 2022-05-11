package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.weather_models.CurrentWeather

interface LocalDataSourceInterface {
    fun insert(weather: CurrentWeather)
    val getWeather: LiveData<CurrentWeather>
}