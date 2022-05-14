package com.example.android.weatherapp.model.remote_source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    fun getWeatherService(): WeatherApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(WeatherApiService::class.java)
    }

}