package com.example.myweatherapp.model.repo

import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.alert_db.AlertEntity
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherEntity
import com.example.myweatherapp.model.pojo.WeatherDataModel

interface RepoInterface {

    // network

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                            lang : String , exclude : String): WeatherDataModel

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double,exclude: String): WeatherDataModel

    // room

    // weather

    fun insertWeatherData(weatherEntity: WeatherEntity)
    fun deleteWeatherData(weatherEntity: WeatherEntity)
    val allStoredWeatherDataModel: LiveData<List<WeatherEntity>>

    // alert

    fun insertAlert(alertEntity: AlertEntity)
    fun deleteAlert(alertEntity: AlertEntity)
    val allStoredWeatherAlerts: LiveData<List<AlertEntity>>

    // location

    fun insertLocationData(locationEntity: LocationEntity)
    fun deleteLocationData(locationEntity: LocationEntity)
    val allStoredLocations : LiveData<List<LocationEntity>>

}