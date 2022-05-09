package com.example.myweatherapp.screens.favourites_screen.view

import com.example.myweatherapp.model.pojo.LocationEntity

interface FavLocationOnClickListener {

    fun onItemClickListener(locationEntity: LocationEntity)
}