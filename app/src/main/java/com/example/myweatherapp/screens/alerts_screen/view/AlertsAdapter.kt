package com.example.myweatherapp.screens.alerts_screen.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.utilities.convertLongToDateAsString
import com.example.myweatherapp.utilities.convertLongToTime

class AlertsAdapter(private val alertOnClickListener: AlertOnClickListener) : RecyclerView.Adapter<AlertsAdapter.ViewHolder>() {

    //var alertLocal = AlertLocal(0,"alarm","1111")
    var alertLocalRecyclerList : List<AlertLocal> = arrayListOf()

    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val alertsTextViewStartDate : TextView = itemView.findViewById(R.id.alertTextViewStartDate)
        val alertsTextViewEndDate : TextView = itemView.findViewById(R.id.alertTextViewEndDate)
        val alertsTextViewType : TextView = itemView.findViewById(R.id.alertTextViewType)
        val alertsTextViewTime : TextView = itemView.findViewById(R.id.alertTextViewTime)
        val alertsTextViewDelete : TextView = itemView.findViewById(R.id.alertTextViewDelete)
        val hourlyConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.alertRowConstraintLayout)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert_row_layout , parent , false)
        return ViewHolder((view))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.alertsTextViewStartDate.text = convertLongToDateAsString(alertLocalRecyclerList[position].startDate)
        holder.alertsTextViewEndDate.text = convertLongToDateAsString(alertLocalRecyclerList[position].endDate)
        holder.alertsTextViewType.text = alertLocalRecyclerList[position].alertType
        holder.alertsTextViewTime.text = convertLongToTime(alertLocalRecyclerList[position].alertTime)
        holder.alertsTextViewDelete.setOnClickListener{alertOnClickListener.onItemClickListener(alertLocalRecyclerList[position])}

    }

    override fun getItemCount(): Int = alertLocalRecyclerList.size


}

