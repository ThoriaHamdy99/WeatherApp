package com.example.android.weatherapp.model.weather_models

data class Alert(
    var sender_name: String,
    var event: String,
    var start: Int,
    var end: Int,
    var description: String,
    var tags: List<String>
)
