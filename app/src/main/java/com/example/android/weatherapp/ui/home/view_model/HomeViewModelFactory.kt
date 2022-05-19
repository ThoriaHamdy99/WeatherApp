package com.example.android.weatherapp.ui.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val repository: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel:: class.java)){
            HomeViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}