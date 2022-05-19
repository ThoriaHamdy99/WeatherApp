package com.example.android.weatherapp.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android.weatherapp.model.local_source.LocalDataSourceInterface
import com.example.android.weatherapp.model.remote_source.RemoteDataSourceInterface
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite
import com.example.android.weatherapp.model.local_source.LocalDataSource
import com.example.android.weatherapp.model.remote_source.RemoteDataSource
import com.example.android.weatherapp.services.SharedPreferencesProvider

class Repository(var remoteInterface: RemoteDataSourceInterface,
                 var localInterface: LocalDataSourceInterface,
                 var context: Context?,
                 var sharedPref: SharedPreferencesProvider
):RepositoryInterface{

   // var storedData: LiveData<CurrentWeather>? = null

    companion object{
        private var instance: Repository? = null
        fun getInstance(context: Context?,
                        sharedPref: SharedPreferencesProvider): Repository {

            return instance?: Repository(RemoteDataSource(context),LocalDataSource(context),
                context, sharedPref)
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

    override fun getAllFavourites(): LiveData<List<Favourite>>? {
        return localInterface.getAllFavourites()
    }

    override fun insertFavourite(favourite: Favourite) {
        localInterface.insertFavourite(favourite)
    }

    override fun deleteFavourite(lat: String, lon: String) {
        localInterface.deleteFavourite(lat, lon)
    }
    //-----------------------------------------------

    //------------Local Source Functions-----------
    override fun insert(currentWeather: CurrentWeather?) {
        localInterface.insert(currentWeather)
    }

//    override suspend fun getWeather(lat: String, lon: String) {
//        storedData = localInterface.getWeather(lat, lon)!!
//    }

    override fun getWeather(): LiveData<CurrentWeather>?{
        return localInterface.getWeather()
    }

    override fun deleteWeather() {
        localInterface.deleteWeather()
    }

    //---------------------------------------

}