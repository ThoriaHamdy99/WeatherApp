package com.example.android.weatherapp.ui.home.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(var repository: RepositoryInterface) : ViewModel() {

    // = null----data didn't observe to fragment
    private var currentWeatherMutableLiveData : MutableLiveData<CurrentWeather> = MutableLiveData()
    var currentWeatherLiveData : LiveData<CurrentWeather> = currentWeatherMutableLiveData

    init {
        fetchWeatherData()
    }
    private fun fetchWeatherData(){
        viewModelScope.launch(Dispatchers.IO) {
            var response = repository.fetchWeatherData()
            Log.i("RESPONSE_VIEW_MODEL", response.toString())
            currentWeatherMutableLiveData?.postValue(repository.fetchWeatherData())
        }

    }

    fun insertWeather(weather: CurrentWeather){
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(weather)
        }
    }

    @JvmName("getWeatherFromDB1")
    fun getWeatherFromDB(): LiveData<CurrentWeather>? {
        return repository.getWeather()
    }
}