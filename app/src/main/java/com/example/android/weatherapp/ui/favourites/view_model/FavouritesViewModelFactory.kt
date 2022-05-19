package com.example.android.weatherapp.ui.favourites.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.weatherapp.model.repository.RepositoryInterface
import java.lang.IllegalArgumentException

class FavouritesViewModelFactory (private val repository: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            FavouritesViewModel(repository) as T
        } else {
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}