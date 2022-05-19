package com.example.android.weatherapp.model.data


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class Hourly(
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @TypeConverters(Converters::class)
    val weather: List<Weather>,
    @SerializedName("wind_speed")
    val windSpeed: Double
)