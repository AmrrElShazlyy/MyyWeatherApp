package com.example.myweatherapp.screens.home_screen.view

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
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.MyLocalDateTime
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.Weather

class HourlyAdapter() :RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {


    var weather1 = Weather(11,"clear sky" , "cleaaar sky" , "01x")
    var hourly1 = Hourly(22,22.22,22,33,44,33.3, listOf(weather1))

    var hourlyList : List<Hourly> = arrayListOf()


    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val hourlyTextViewTime : TextView = itemView.findViewById(R.id.hourlyTextViewTime)
        val hourlyTextViewTemp : TextView = itemView.findViewById(R.id.hourlyTextViewTemp)
        val hourlyImageViewIcon : ImageView = itemView.findViewById(R.id.hourlyImageViewIcon)
        val hourlyConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.hourlyRowConstarintLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_row_layout , parent , false)
        return ViewHolder((view))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var hourlyIcon : String = hourlyList[position].weather?.get(0)?.icon!!
        var hourlyIconUrl : String = "${Constants.IMG_URL+hourlyIcon}.png"
        Glide.with(holder.hourlyImageViewIcon.context).load(hourlyIconUrl).into(holder.hourlyImageViewIcon)

        holder.hourlyTextViewTime.text = MyLocalDateTime.getTimeFromHourlyObj(hourlyList[position])

        // *****************   add when temp get in C or K or F  *********************
        holder.hourlyTextViewTemp.text = "${(hourlyList[position].temp).toString()} 'K"

    }

    override fun getItemCount(): Int = hourlyList.size
}


