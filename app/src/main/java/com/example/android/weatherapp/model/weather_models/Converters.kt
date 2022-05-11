package com.example.android.weatherapp.model.weather_models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    var gson = Gson()

    @TypeConverter
    fun listOfHourlyoString(listOfHouly: List<Hourly>): String {
        return Gson().toJson(listOfHouly).toString()
    }

    @TypeConverter
    fun stringToListOfHourly(value: String): List<Hourly> {
        val listOfHouly = object : TypeToken<ArrayList<Hourly>>() {}.type
        return Gson().fromJson(value, listOfHouly)
    }



    @TypeConverter
    fun listOfDailyToString(listOfDaily: List<Daily>): String {
        return Gson().toJson(listOfDaily).toString()
    }

    @TypeConverter
    fun stringToListOfDaily(value: String): List<Daily> {
        val listOfDaily = object : TypeToken<ArrayList<Daily>>() {}.type
        return Gson().fromJson(value, listOfDaily)
    }



    @TypeConverter
    fun listOfAlertsToString(listOfAlert: List<Alert>?): String? {
        return if(listOfAlert == null) null else gson.toJson(listOfAlert)
    }

    @TypeConverter
    fun stringToListOfAlarts(value: String?): List<Alert>? {
        return if (value == null) null else gson.fromJson(value, object : TypeToken<List<Weather>?>() {}.type)
    }

    @TypeConverter
    fun currentToString(current: Current): String {
        return Gson().toJson(current).toString()
    }

    @TypeConverter
    fun stringToCurrent(value: String): Current {
        val current = object : TypeToken<Current>() {}.type
        return Gson().fromJson(value, current)
    }
}