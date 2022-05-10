package com.example.myweatherapp.screens.alerts_screen.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.AlertLocal

class AlertsAdapter(private val alertOnClickListener: AlertOnClickListener) : RecyclerView.Adapter<AlertsAdapter.ViewHolder>() {

    //var alertLocal = AlertLocal(0,"alarm","1111")
    var alertLocalRecyclerList : List<AlertLocal> = arrayListOf()

    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val alertsTextViewTime : TextView = itemView.findViewById(R.id.alertTextViewTime)
        val alertsTextViewTYpe : TextView = itemView.findViewById(R.id.alertTextViewType)
        val alertsTextViewDelete : TextView = itemView.findViewById(R.id.alertTextViewDelete)
        val hourlyConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.alertRowConstraintLayout)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert_row_layout , parent , false)
        return ViewHolder((view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.alertsTextViewTime.text = alertLocalRecyclerList[position].time
        holder.alertsTextViewTYpe.text = alertLocalRecyclerList[position].type
        holder.alertsTextViewDelete.setOnClickListener{alertOnClickListener.onItemClickListener(alertLocalRecyclerList[position])}

    }

    override fun getItemCount(): Int = alertLocalRecyclerList.size


}



/*


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

        when(hourlyUnits){
            Constants.myUnitStandard -> { holder.hourlyTextViewTemp.text = "${(hourlyList[position].temp).toString()} °K "}
            Constants.myUnitMetric -> { holder.hourlyTextViewTemp.text = "${(hourlyList[position].temp).toString()} ℃ "}
            Constants.myUnitImperial -> { holder.hourlyTextViewTemp.text = "${(hourlyList[position].temp).toString()} °F "}
        }

    }

    override fun getItemCount(): Int = hourlyList.size
 */