package com.example.myweatherapp.screens.alerts_screen.view

import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.pojo.LocationEntity

interface AlertOnClickListener {

    fun onItemClickListener(alertLocal: AlertLocal)
}