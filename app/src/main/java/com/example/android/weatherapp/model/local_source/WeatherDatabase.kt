package com.example.android.weatherapp.model.local_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.weatherapp.model.data.Converters
import com.example.android.weatherapp.model.data.CurrentWeather

@Database(entities = [CurrentWeather::class], version = 1)
@TypeConverters(Converters::class)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDAO

    companion object{
        private var instance: WeatherDatabase? = null
        fun getInstance(context: Context): WeatherDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context,
                    WeatherDatabase::class.java,"weather").build();
            }
            return instance as WeatherDatabase
        }
    }
}