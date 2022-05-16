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

//    override suspend fun getWeatherData(): CurrentWeather?{
//        if(sharedPref.isTheInternetEnabled){
//            return fetchWeatherData()
//        }
//        return getWeather(sharedPref.latLong[0], sharedPref.latLong[1])
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

    override suspend fun getWeather(): LiveData<CurrentWeather>? {
        return localInterface.getWeather()
    }

    override suspend fun deleteWeather() {
        localInterface.deleteWeather()
    }

    override suspend fun deleteAll() {
        localInterface.deleteAll()
    }
    //---------------------------------------

}