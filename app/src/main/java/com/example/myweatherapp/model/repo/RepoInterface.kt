package com.example.myweatherapp.model.repo

import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.AlertEntity
import com.example.myweatherapp.database.WeatherEntity
import com.example.myweatherapp.model.weather_data_model.Alert
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

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

}