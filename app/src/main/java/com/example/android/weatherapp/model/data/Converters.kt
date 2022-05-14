package com.example.android.weatherapp.model.data

import androidx.room.TypeConverter
import com.example.android.weatherapp.model.data.Alert
import com.example.android.weatherapp.model.data.Current
import com.example.android.weatherapp.model.data.Daily
import com.example.android.weatherapp.model.data.Hourly
import com.example.android.weatherapp.model.data.Temp
import com.example.android.weatherapp.model.data.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//fromHourlyItemList- fromDailyList-fromAlertItemList- fromWeatherItemList- fromCurrentItemList
//fromTempList-
class Converters {
    var gson = Gson()

    @TypeConverter
    fun fromHourlyItemList(value: MutableList<Hourly>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Hourly>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toHourlyItemList(value: String): MutableList<Hourly> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Hourly>>() {}.type
        return gson.fromJson(value, type)
    }


    @TypeConverter
    fun fromDailyList(value: MutableList<Daily>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Daily>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDailyList(value: String): MutableList<Daily> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Daily>>() {}.type
        return gson.fromJson(value, type)
    }



    @TypeConverter
    fun fromAlertItemList(value: MutableList<Alert>?): String? {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Alert>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAlertItemList(value: String?): MutableList<Alert>? {
        if (value == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<MutableList<Alert>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromWeatherItemList(value: MutableList<Weather>): String {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Weather>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherItemList(value: String): MutableList<Weather> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Weather>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCurrentItemList(value: Current): String {
        val gson = Gson()
        val type = object : TypeToken<Current>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCurrentItemList(value: String): Current {
        val gson = Gson()
        val type = object : TypeToken<Current>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromTempList(value: Temp): String {
        val gson = Gson()
        val type = object : TypeToken<Temp>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTempList(value: String): Temp {
        val gson = Gson()
        val type = object : TypeToken<Temp>() {}.type
        return gson.fromJson(value, type)
    }
}