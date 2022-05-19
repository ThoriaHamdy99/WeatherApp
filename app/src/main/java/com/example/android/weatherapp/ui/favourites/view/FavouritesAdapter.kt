package com.example.android.weatherapp.ui.favourites.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weatherapp.R
import com.example.android.weatherapp.model.data.Favourite

class FavouritesAdapter(var onClickListener: OnClickListener): RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>() {

    var favouriteList: List<Favourite> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavouritesViewHolder(layoutInflater.inflate(R.layout.favourites_row, parent,
            false))
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        holder.countryName.text = favouriteList.get(position).toString()
        holder.deleteBtn.setOnClickListener {
            onClickListener.onDeleteBtnClicked(favourite = favouriteList[position])
        }

        holder.view.setOnClickListener{
            onClickListener.onFavouriteClicked(favourite = favouriteList[position])
        }
    }

    override fun getItemCount(): Int {
        return favouriteList.size
    }

    @JvmName("setFavouriteList1")
    fun setFavouriteList(favourites: List<Favourite>){
        this.favouriteList = favourites
        notifyDataSetChanged()
    }

    class FavouritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryName: TextView = itemView.findViewById(R.id.favourite_country_name)
        var deleteBtn: ImageButton = itemView.findViewById(R.id.favourite_delete_button)
        var view: View = itemView
    }
}