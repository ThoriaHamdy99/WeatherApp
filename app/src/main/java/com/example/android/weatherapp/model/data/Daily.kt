package com.example.android.weatherapp.model.data


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)
data class Daily(
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Int,
    @TypeConverters(Converters::class)
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val humidity: Int,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    @TypeConverters(Converters::class)
    val weather: List<Weather>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double
)