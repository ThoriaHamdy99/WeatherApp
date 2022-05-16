package com.example.android.weatherapp.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.local_source.LocalDataSourceInterface
import com.example.android.weatherapp.model.remote_source.RemoteDataSourceInterface
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.local_source.LocalDataSource
import com.example.android.weatherapp.model.remote_source.RemoteDataSource
import com.example.android.weatherapp.services.SharedPreferencesProvider

class Repository(var remoteInterface: RemoteDataSourceInterface,
                 var localInterface: LocalDataSourceInterface,
                 var context: Context?,
                var sharedPref: SharedPreferencesProvider):RepositoryInterface{

    companion object{
        private var instance: Repository? = null
        fun getInstance(context: Context?,
                        sharedPref: SharedPreferencesProvider): Repository {

            return instance?: Repository(RemoteDataSource(context),LocalDataSource(context),
                context, sharedPref = sharedPref)
        }
    }

//    fun getWeatherData(sharedPref: SharedPreferencesProvider): CurrentWeather{
//        if (sharedPref.latLong)
//    }


    //-----------Remote Source Functions------------
    override suspend fun fetchWeatherData(): CurrentWeather? {
        return remoteInterface.fetchWeatherData(sharedPref)
    }
    //-----------------------------------------------


    //------------Local Source Functions-----------
    override suspend fun insert(currentWeather: CurrentWeather?) {
        localInterface.insert(currentWeather)
    }

    override suspend fun getWeather(lat: String?, lon: String?): LiveData<CurrentWeather>? {
        return localInterface.getWeather(lat, lon)
    }

    override suspend fun deleteWeather(lat: String, lon: String) {
        localInterface.deleteWeather(lat, lon)
    }

    override suspend fun deleteAll() {
        localInterface.deleteAll()
    }
    //---------------------------------------

}