package com.example.android.weatherapp.ui.home.view

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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeDailyAdapter(var context: Context): RecyclerView.Adapter<HomeDailyAdapter.DailyViewHolder>() {

    private var daily: List<Daily> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DailyViewHolder(layoutInflater.inflate(R.layout.daily_list_row, parent, false))
    }

    fun setDailyList(daily: List<Daily>){
        this.daily = daily
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {


        holder.title.text = getDayName(daily[position].dt.toLong())
        holder.temp.text = daily[position].temp.day.toString()
        holder.status.text = daily[position].weather[0].description
        val urlIcon = "https://openweathermap.org/img/wn/${daily[position].weather[0].icon}@2x.png"
        context?.let { Glide.with(it).asBitmap().load(urlIcon).into(holder.image) }

    }

    private fun getDayName(dt : Long): String{
        var date: Date = Date(dt * 1000)
        var dateFormat : DateFormat = SimpleDateFormat("EEEE")
        return dateFormat.format(date)
    }

    override fun getItemCount(): Int {
        var size = daily.size
        return daily.size
    }

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.daily_row_title)
        var temp: TextView = itemView.findViewById(R.id.weekly_row_temp)
        var image: ImageView = itemView.findViewById(R.id.daily_row_image_view)
        var status: TextView = itemView.findViewById(R.id.daily_row_status)
    }
}