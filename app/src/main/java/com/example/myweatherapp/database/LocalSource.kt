package com.example.myweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweatherapp.database.alert_db.AlertEntity
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherEntity

interface LocalSource {

    // weather

    fun insertWeatherData(weatherEntity: WeatherEntity)
    fun deleteWeatherData(weatherEntity: WeatherEntity)
    val allStoredWeatherDataModel: LiveData<List<WeatherEntity>>

    // alert

    fun insertAlert(alertEntity : AlertEntity)
    fun deleteAlert(alertEntity: AlertEntity)
    val allStoredWeatherAlerts: LiveData<List<AlertEntity>>

    // location

    fun insertLocationData(locationEntity: LocationEntity)
    fun deleteLocationData(locationEntity: LocationEntity)
    val allStoredLocations : LiveData<List<LocationEntity>>
}