package com.example.android.weatherapp.model.local_source

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import kotlinx.coroutines.CoroutineScope

class LocalDataSource(val context: Context?): LocalDataSourceInterface {

    private lateinit var storedWeather: LiveData<CurrentWeather>
    private var weatherDao: WeatherDAO? = null

    init {
        val weatherDatabase = context?.let { WeatherDatabase.getInstance(it.applicationContext) }
        weatherDao = weatherDatabase!!.weatherDao()
    }

    @WorkerThread
    override suspend fun insert(currentWeather: CurrentWeather?) {
        weatherDao?.insert(currentWeather)
    }

    @WorkerThread
    override fun getWeather(): LiveData<CurrentWeather>? {
        return weatherDao?.getWeather()
    }

    @WorkerThread
    override suspend fun deleteWeather() {
        weatherDao?.deleteWeather()
    }

    @WorkerThread
    override suspend fun deleteAll() {
        weatherDao?.deleteAll()
    }
}