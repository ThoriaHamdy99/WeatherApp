package com.example.android.weatherapp.model.remote_source

import android.view.textclassifier.TextLanguage
import com.example.android.weatherapp.model.data.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("onecall")
    suspend fun getCurrentWeather(@Query("lat") lat: String?,
                                  @Query("lon") lon: String?,
                                  @Query("exclude") exclude:String,
                                  @Query("units") units:String,
                                  @Query("lang") lang:String,
                                  @Query("appid") appid:String
    ): Response<CurrentWeather>

}