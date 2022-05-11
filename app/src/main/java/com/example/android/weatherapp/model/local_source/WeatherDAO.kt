package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.weatherapp.model.weather_models.CurrentWeather

@Dao
interface WeatherDAO {
    @get:Query("SELECT * From weather")
    val getWeather: LiveData<CurrentWeather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: CurrentWeather)
}