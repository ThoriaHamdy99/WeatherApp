package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.weatherapp.model.data.CurrentWeather

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currentWeather: CurrentWeather?)

    @Query("SELECT * From CurrentWeather WHERE lat=:lat AND lon=:lon")
    fun getWeather(lat: String?, lon: String?): LiveData<CurrentWeather>

    @Query("Delete from CurrentWeather WHERE lat=:lat AND lon=:lng  ")
    suspend fun deleteWeather(lat:String , lng:String)

    @Query("Delete  from CurrentWeather ")
    suspend fun deleteAll()
}