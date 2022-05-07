package com.example.myweatherapp.home_screen.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.constants.Constants
import com.example.myweatherapp.constants.MyLocalDateTime
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Temp
import com.example.myweatherapp.model.pojo.Weather

class DailyAdapter() : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    var weather1 = Weather(11,"clear sky" , "cleaaar sky" , "01x")
    var temp1 = Temp(33.33,33.33,33.33,33.33,33.33,33.33)
    var daily1 = Daily(22,temp1,44,44,33,22.33, listOf(weather1))

    var dailyList : List<Daily> = arrayListOf()

    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val dailyTextViewDay : TextView = itemView.findViewById(R.id.dailyTextViewDay)
        val dailyTextViewDescr : TextView = itemView.findViewById(R.id.dailyTextViewDescr)
        val dailyTextViewMaxMinTemp : TextView = itemView.findViewById(R.id.dailyTextViewMaxMinTemp)
        val dailyImageViewIcon : ImageView = itemView.findViewById(R.id.dailyImageViewIcon)

        val dailyConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.dailyRowConstraintLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_row_layout , parent , false)
        return ViewHolder((view))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var daily : Daily = dailyList[position]
        var minTemp = (daily.temp?.min)?.toInt().toString()
        var maxTemp = (daily.temp?.max)?.toInt().toString()

        holder.dailyTextViewDay.text = MyLocalDateTime.getDayFromDailyObj(daily)

        var dailyIcon : String = daily.weather?.get(0)?.icon!!
        var dailyIconUrl : String = "${Constants.IMG_URL+dailyIcon}.png"
        Glide.with(holder.dailyImageViewIcon.context).load(dailyIconUrl).into(holder.dailyImageViewIcon)

        holder.dailyTextViewDescr.text = daily.weather?.get(0)?.description

        // *****************   add when temp get in C or K or F  *********************
        holder.dailyTextViewMaxMinTemp.text = "$maxTemp / $minTemp 'K "




    }

    override fun getItemCount(): Int = dailyList.size


}