package com.example.android.weatherapp.model.data


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class Hourly(
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    @TypeConverters(Converters::class)
    val weather: List<Weather>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)