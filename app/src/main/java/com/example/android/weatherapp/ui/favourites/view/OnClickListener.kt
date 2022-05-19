package com.example.android.weatherapp.ui.favourites.view

import com.example.android.weatherapp.model.data.Favourite

interface OnClickListener {
    open fun onDeleteBtnClicked(favourite: Favourite)
    open fun onFavouriteClicked(favourite: Favourite)
}