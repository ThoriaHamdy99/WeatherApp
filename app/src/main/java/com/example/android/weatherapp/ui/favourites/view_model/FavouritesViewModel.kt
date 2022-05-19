package com.example.android.weatherapp.ui.favourites.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.weatherapp.model.data.Favourite
import com.example.android.weatherapp.model.repository.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel(var repository: RepositoryInterface): ViewModel() {

    fun insertFavourite(weather: Favourite){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertFavourite(weather)
        }
    }

    fun getAllFavourites(): LiveData<List<Favourite>>{
        return repository.getAllFavourites()!!

    }

    fun deleteFavourite(weather: Favourite){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteFavourite(weather.countryName)
        }
    }
}