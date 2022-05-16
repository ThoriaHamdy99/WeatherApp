package com.example.android.weatherapp.model.remote_source

import android.content.Context
import android.util.Log
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.TempAndWindSpeedUnit
import com.example.android.weatherapp.services.SharedPreferencesProvider
import retrofit2.Response

class RemoteDataSource(val context: Context?): RemoteDataSourceInterface {

    private val API_KEY = "fc9624bf360991a83e6c82fa2996bec3"

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
            latPref, lonPref, "minutely", unit, "en", API_KEY)

        if (response.isSuccessful){
            Log.i("RESPONSE_API", "My response" + response.body().toString())
            Log.i("RESPONSE_API", "lat = ${sharedPref.latLong[0]}, Lon = ${sharedPref.latLong[1]}")
            return response.body()
        }
        else{
            Log.i("RESPONSE_API", "My response" + response.body().toString())
            Log.i("RESPONSE_API", "lat = ${sharedPref.latLong[0]}, Lon = ${sharedPref.latLong[1]}")
            return null
        }
    }
}