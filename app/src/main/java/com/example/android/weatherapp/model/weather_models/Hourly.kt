package com.example.android.weatherapp.model.weather_models

import com.google.gson.annotations.SerializedName

data class Hourly(
    var clouds: Int,
    @SerializedName("dew_point")
    var dewPoint: Double,
    var dt: Int,
    @SerializedName("feels_like")
    var feelsLike: Double,
    var humidity: Int,
    var pop: Double,
    var pressure: Int,
    var temp: Double,
    var uvi: Double,
    var visibility: Int,
    var weather: List<Weather>,
    @SerializedName("wind_deg")
    var windDeg: Int,
    @SerializedName("wind_gust")
    var windGust: Double,
    @SerializedName("wind_speed")
    var windSpeed: Double
)
