package com.example.myweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweatherapp.model.weather_data_model.Alert
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

interface LocalSource {

    // weather

    fun insertWeatherData(weatherEntity: WeatherEntity)
    fun deleteWeatherData(weatherEntity: WeatherEntity)
    val allStoredWeatherDataModel: LiveData<List<WeatherEntity>>

    // alert

    fun insertAlert(alertEntity : AlertEntity)
    fun deleteAlert(alertEntity: AlertEntity)
    val allStoredWeatherAlerts: LiveData<List<AlertEntity>>
}