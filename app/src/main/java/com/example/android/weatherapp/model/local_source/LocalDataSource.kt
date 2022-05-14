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
    override fun getWeather(lat: String?, lon: String?): LiveData<CurrentWeather>? {
        return weatherDao?.getWeather(lat, lon)
    }

    @WorkerThread
    override suspend fun deleteWeather(lat: String, lon: String) {
        weatherDao?.deleteWeather(lat, lon)
    }

    @WorkerThread
    override suspend fun deleteAll() {
        weatherDao?.deleteAll()
    }
}