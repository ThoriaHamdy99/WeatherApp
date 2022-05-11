package com.example.android.weatherapp.model.weather_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "weather")
@TypeConverters(Converters::class)
data class CurrentWeather(
    val lat: Double?,
    val lon: Double?,
    @TypeConverters(Converters::class)
    var current: Current,
    @TypeConverters(Converters::class)
    var daily: List<Daily>,
    @TypeConverters(Converters::class)
    var hourly: List<Hourly>,
    var timezone: String,
    @SerializedName("timezone_offset")
    var timezoneOffset: Int,
    @TypeConverters(Converters::class)
    var alerts: List<Alert>?) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}





