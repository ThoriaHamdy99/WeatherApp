package com.example.android.weatherapp.model.data


import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@TypeConverters(Converters::class)

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    @SerializedName("sender_name")
    val senderName: String,
    val start: Int,
    val tags: List<String>
)