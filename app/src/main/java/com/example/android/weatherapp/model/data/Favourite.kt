package com.example.android.weatherapp.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Favourites")
data class Favourite(
    val lat: Double,
    val lon: Double,
    @PrimaryKey(autoGenerate = false)
    val countryName: String
): Serializable
