package com.example.android.weatherapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.weatherapp.R
import com.example.android.weatherapp.model.data.Hourly
import java.text.SimpleDateFormat
import java.util.*

class HomeHourlyAdapter(var context: Context): RecyclerView.Adapter<HomeHourlyAdapter.HourlyViewHolder>() {

    private var hourly: List<Hourly> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HourlyViewHolder(layoutInflater.inflate(R.layout.daily_list_row, parent, false))
    }

    fun setHourlyList(hourly: List<Hourly>){
        this.hourly = hourly
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat("hh:mm")
        val format = simpleDateFormat.format(hourly[position]?.dt?.times(1000L))
        if (position <= 11){
            holder.hour.text = "$format AM"
        }
        else{
            holder.hour.text = "$format PM"
        }
        holder.temp.text = hourly[position].temp.toString()

        val urlIcon = "http://openweathermap.org/img/wn/${hourly[position].weather[0].icon}@2x.png"

        context?.let { Glide.with(it).asBitmap().load(urlIcon).into(holder.image) }

    }

    override fun getItemCount(): Int {
        return hourly.size
    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hour: TextView = itemView.findViewById(R.id.daily_row_hour)
        var temp: TextView = itemView.findViewById(R.id.daily_row_temp)
        var image: ImageView = itemView.findViewById(R.id.daily_row_image)
    }
}