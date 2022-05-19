package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite

interface LocalDataSourceInterface {
    fun insert(currentWeather: CurrentWeather?)
    fun getWeather(): LiveData<CurrentWeather>?
    fun deleteWeather()

    fun getAllFavourites(): LiveData<List<Favourite>>?
    fun insertFavourite(favourite: Favourite)
    fun deleteFavourite(lat: String, lon: String)
}