package com.example.android.weatherapp.model.local_source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite

@Dao
interface WeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currentWeather: CurrentWeather?)

    @Query("SELECT * From CurrentWeather")
    fun getWeather(): LiveData<CurrentWeather>

    @Query("Delete from CurrentWeather")
    fun deleteWeather()

    @Query("SELECT * From Favourites")
    fun getAllFavourites(): LiveData<List<Favourite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favourite: Favourite)

    @Query("Delete from Favourites where lat like :lat and lon like :lon")
    fun deleteFavourite(lat: String, lon: String)

}