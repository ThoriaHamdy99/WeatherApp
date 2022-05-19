package com.example.android.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite
import com.example.android.weatherapp.services.SharedPreferencesProvider

interface RepositoryInterface {
    fun insert(currentWeather: CurrentWeather?)
    fun getWeather(): LiveData<CurrentWeather>?
    fun deleteWeather()
    suspend fun fetchWeatherData(): CurrentWeather?
    fun insertFavourite(favourite: Favourite)
    fun deleteFavourite(lat: String, lon: String)
    fun getAllFavourites(): LiveData<List<Favourite>>?
}
