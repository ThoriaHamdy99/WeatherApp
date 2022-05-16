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
import com.example.android.weatherapp.model.data.Daily

class HomeDailyAdapter(var context: Context): RecyclerView.Adapter<HomeDailyAdapter.DailyViewHolder>() {

    private var daily: List<Daily> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DailyViewHolder(layoutInflater.inflate(R.layout.weekly_list_row, parent, false))
    }

    fun setDailyList(daily: List<Daily>){
        this.daily = daily
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
//        if (position <= 11){
//            holder.hour.text = "${position + 1} AM"
//        }
//        else{
//            holder.hour.text = "${position + 1} PM"
//        }
        holder.temp.text = daily[position].temp.day.toString()
        holder.status.text = daily[position].weather[0].description
        val urlIcon = "http://openweathermap.org/img/wn/${daily[position].weather[0].icon}@2x.png"
        context?.let { Glide.with(it).asBitmap().load(urlIcon).into(holder.image) }

    }

    private fun getDayName(){

    }

    override fun getItemCount(): Int {
        return daily.size
    }

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.weakly_row_title)
        var temp: TextView = itemView.findViewById(R.id.weekly_row_temp)
        var image: ImageView = itemView.findViewById(R.id.weakly_row_image)
        var status: TextView = itemView.findViewById(R.id.weekly_row_status)
    }
}