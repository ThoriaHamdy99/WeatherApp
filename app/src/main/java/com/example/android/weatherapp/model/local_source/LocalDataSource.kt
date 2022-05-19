package com.example.android.weatherapp.model.local_source

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite
import kotlinx.coroutines.CoroutineScope

class LocalDataSource(val context: Context?): LocalDataSourceInterface {

    private lateinit var storedWeather: LiveData<CurrentWeather>
    private var weatherDao: WeatherDAO? = null

    init {
        val weatherDatabase = context?.let { WeatherDatabase.getInstance(it.applicationContext) }
        weatherDao = weatherDatabase!!.weatherDao()
    }

    override fun insert(currentWeather: CurrentWeather?) {
        weatherDao?.insert(currentWeather)
    }

    override fun getWeather(): LiveData<CurrentWeather>? {
        return weatherDao?.getWeather()
    }

    override fun deleteWeather() {
        weatherDao?.deleteWeather()
    }

    override fun getAllFavourites(): LiveData<List<Favourite>>? {
        return weatherDao?.getAllFavourites()
    }

    override fun insertFavourite(favourite: Favourite) {
        weatherDao?.insertFavourite(favourite)
    }

    override fun deleteFavourite(countryName: String) {
        weatherDao?.deleteFavourite(countryName)
    }
}