package com.example.android.weatherapp.model.data


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class Daily(
    val dt: Int,
    val humidity: Int,
    val pressure: Int,
    val temp: Temp,
    @TypeConverters(Converters::class)
    val weather: List<Weather>
)