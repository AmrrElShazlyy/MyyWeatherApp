package com.example.myweatherapp.database.app_db_datasource

import androidx.lifecycle.LiveData
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.WeatherDataModel

interface LocalSource {

    // weather

    fun insertWeatherData(weatherDataModel: WeatherDataModel)
    fun deleteWeatherData(weatherDataModel: WeatherDataModel)
    val allStoredWeatherDataModel: LiveData<List<WeatherDataModel>>

    // alert

    fun insertAlert(alert : Alert)
    fun deleteAlert(alert: Alert)
    val allStoredWeatherAlerts: LiveData<List<Alert>>

    // location

    fun insertLocationData(locationEntity: LocationEntity)
    fun deleteLocationData(locationEntity: LocationEntity)
    val allStoredLocations : LiveData<List<LocationEntity>>
}