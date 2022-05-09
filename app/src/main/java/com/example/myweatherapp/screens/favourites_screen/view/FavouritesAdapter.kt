package com.example.myweatherapp.screens.favourites_screen.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.screens.home_screen.view.HourlyAdapter

class FavouritesAdapter(private val favLocationOnClickListener: FavLocationOnClickListener) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    var locationEntity = LocationEntity("cairoooooo",0.0,0.0)
    var locationEntityListRecycler : List<LocationEntity> = arrayListOf()

    inner class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){

        val favouritesTextViewCity : TextView = itemView.findViewById(R.id.favouritesTextViewCity)
        val favouritesConstraintLayout : ConstraintLayout = itemView.findViewById(R.id.favouritesRowConstraintLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourites_row_layout , parent , false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.favouritesTextViewCity.text = locationEntityListRecycler[position].cityName
        holder.favouritesConstraintLayout.setOnClickListener {
            Log.e("****", "onBindViewHolder:click on constraaaint ", )
            favLocationOnClickListener.onItemClickListener(locationEntityListRecycler[position])
        }
    }

    override fun getItemCount(): Int = locationEntityListRecycler.size

}
