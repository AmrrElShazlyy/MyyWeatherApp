package com.example.myweatherapp.screens.favourites_screen.view

import com.example.myweatherapp.model.pojo.LocationEntity

interface FavLocationOnClickListener {

    fun deleteLocationFromDb(locationEntity: LocationEntity)
    fun onItemClickListener(locationEntity: LocationEntity)
}