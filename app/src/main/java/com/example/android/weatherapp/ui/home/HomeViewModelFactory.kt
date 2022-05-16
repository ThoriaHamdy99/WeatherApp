package com.example.android.weatherapp.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val repository: RepositoryInterface,
                    private val sharedPref: SharedPreferencesProvider): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel:: class.java)){
            HomeViewModel(repository, sharedPref) as T
        }
        else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}