package com.example.android.weatherapp.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.local_source.LocalDataSourceInterface
import com.example.android.weatherapp.model.remote_source.RemoteDataSourceInterface
import com.example.android.weatherapp.model.weather_models.CurrentWeather

class Repository(remoteInterface: RemoteDataSourceInterface,
                 var localInterface: LocalDataSourceInterface,
                 var context: Context?):RepositoryInterface{

    companion object{
        private var instance: Repository? = null
        fun getInstance(remoteInterface:RemoteDataSourceInterface,
                        localInterface: LocalDataSourceInterface, context: Context?): Repository {

            return instance?: Repository(remoteInterface,localInterface,context)
        }
    }

    override suspend fun getWeather(
        lat: String,
        lon: String,
        units: String,
        lang: String,
    ): CurrentWeather {
        TODO("Not yet implemented")
    }

    override fun insert(weather: CurrentWeather) {
        TODO("Not yet implemented")
    }

    override val getWeather: LiveData<CurrentWeather>
        get() = TODO("Not yet implemented")

}