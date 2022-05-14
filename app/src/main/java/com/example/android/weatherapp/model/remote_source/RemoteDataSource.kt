package com.example.android.weatherapp.model.remote_source

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.TempAndWindSpeedUnit
import com.example.android.weatherapp.services.SharedPreferencesProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemoteDataSource(val application: Application): RemoteDataSourceInterface {

    private val Api_Key = "fc9624bf360991a83e6c82fa2996bec3"

    override suspend fun fetchWeatherData(sharedPref: SharedPreferencesProvider): CurrentWeather? {
        val latLong = sharedPref.latLong

        val latPref = latLong[0].toString()
        val lonPref = latLong[1].toString()
        val  unit = sharedPref.getUnit.toString()

        if (unit == "imperial") {
            TempAndWindSpeedUnit.tempUnit = "F"
            TempAndWindSpeedUnit.WindSpeedUnit = "miles/hr"
        } else if (unit == "metric") {
            TempAndWindSpeedUnit.tempUnit = "C"
            TempAndWindSpeedUnit.WindSpeedUnit = "m/s"
        }
        val response = WeatherClient.getWeatherService().getCurrentWeather(
            latPref, lonPref, "minutely", unit, Api_Key)
        if (response.isSuccessful)
            return response.body()
        else
            return null
        Log.d("responseeeeeeee", latPref + unit + "--------" + lonPref)

    }
}