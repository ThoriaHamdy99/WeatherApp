package com.example.android.weatherapp.model.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CurrentWeather")
@TypeConverters(Converters::class)
data class CurrentWeather(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    @TypeConverters(Converters::class)
    val alerts: List<Alert>,
    @TypeConverters(Converters::class)
    val current: Current,
    @TypeConverters(Converters::class)
    val daily: List<Daily>,
    @TypeConverters(Converters::class)
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)