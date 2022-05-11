package com.example.android.weatherapp.model.weather_models

import com.google.gson.annotations.SerializedName

data class Daily(
    var clouds: Int,
    @SerializedName("dew_point")
    var dewPoint: Double,
    var dt: Int,
    @SerializedName("feels_like")
    var feelsLike: FeelsLike,
    var humidity: Int,
    @SerializedName("moon_phase")
    var moonPhase: Double,
    var moonrise: Int,
    var moonset: Int,
    var pop: Double,
    var pressure: Int,
    var sunrise: Int,
    var sunset: Int,
    var temp: Temp,
    var uvi: Double,
    var weather: List<Weather>,
    @SerializedName("wind_deg")
    var windDeg: Int,
    @SerializedName("wind_gust")
    var windGust: Double,
    @SerializedName("wind_speed")
    var windSpeed: Double
)
