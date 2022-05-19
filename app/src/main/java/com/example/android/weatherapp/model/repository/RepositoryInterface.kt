package com.example.android.weatherapp.model.repository

import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite

interface RepositoryInterface {
    fun insert(currentWeather: CurrentWeather?)
    fun getWeather(): LiveData<CurrentWeather>?
    fun deleteWeather()
    suspend fun fetchWeatherData(isFavourite: Boolean): CurrentWeather?
    fun insertFavourite(favourite: Favourite)
    fun deleteFavourite(countryName: String)
    fun getAllFavourites(): LiveData<List<Favourite>>?
}
