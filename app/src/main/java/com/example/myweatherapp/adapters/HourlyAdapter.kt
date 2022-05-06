package com.example.myweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.Weather

class HourlyAdapter() :RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {


    var weather1 = Weather(11,"clear sky" , "cleaaar sky" , "01x")
    var hourly1 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))
    var hourly2 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))
    var hourly3 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))
    var hourly4 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))
    var hourly5 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))

    var hourlyList : List<Hourly> = arrayListOf(hourly1,hourly2,hourly3,hourly4,hourly5)


    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val hourlyTextViewTime : TextView = itemView.findViewById(R.id.hourlyTextViewTime)
        val hourlyTextViewTemp : TextView = itemView.findViewById(R.id.hourlyTextViewTemp)
        val hourlyImageViewIcon : ImageView = itemView.findViewById(R.id.hourlyImageViewIcon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_row_layout , parent , false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.hourlyImageViewIcon.context).load(hourlyList[position].weather?.get(0)?.icon).into(holder.hourlyImageViewIcon)
        holder.hourlyTextViewTime.text = (hourlyList[position].dt).toString()
        holder.hourlyTextViewTemp.text = (hourlyList[position].temp).toString()

    }

    override fun getItemCount(): Int = hourlyList.size
}


