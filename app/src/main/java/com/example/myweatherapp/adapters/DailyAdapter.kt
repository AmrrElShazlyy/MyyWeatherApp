package com.example.myweatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.Temp
import com.example.myweatherapp.model.pojo.Weather

class DailyAdapter() : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    var weather1 = Weather(11,"clear sky" , "cleaaar sky" , "01x")
    var temp1 = Temp(33.33,33.33,33.33,33.33,33.33,33.33)
    var daily1 = Daily(22,temp1,44,44,33,22.33, listOf(weather1))

    var dailyList : List<Daily> = arrayListOf(daily1,daily1,daily1,daily1,daily1)

    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val dailyTextViewDay : TextView = itemView.findViewById(R.id.dailyTextViewDay)
        val dailyTextViewMaxMinTemp : TextView = itemView.findViewById(R.id.dailyTextViewMaxMinTemp)
        val dailyImageViewIcon : ImageView = itemView.findViewById(R.id.dailyImageViewIcon)
        val dailyConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.dailyRowConstraintLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_row_layout , parent , false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var daily : Daily = dailyList[position]
        var minTemp = (daily.temp?.min)?.toInt().toString()
        var maxTemp = (daily.temp?.max)?.toInt().toString()

        Glide.with(holder.dailyImageViewIcon.context).load(dailyList[position].weather?.get(0)?.icon).into(holder.dailyImageViewIcon)
        holder.dailyTextViewDay.text = (dailyList[position].dt).toString()
        holder.dailyTextViewMaxMinTemp.text = "$maxTemp / $minTemp ℃ "

    }

    override fun getItemCount(): Int = dailyList.size


}