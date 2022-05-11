package com.example.android.weatherapp.model.local_source

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.weather_models.CurrentWeather

class LocalDataSource(val context: Context?): LocalDataSourceInterface {

    override fun insert(weather: CurrentWeather) {
        TODO("Not yet implemented")
    }

    override val getWeather: LiveData<CurrentWeather>
        get() = TODO("Not yet implemented")
}