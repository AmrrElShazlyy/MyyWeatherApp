package com.example.myweatherapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.alert_db.AlertDao
import com.example.myweatherapp.database.location_db.LocationDao
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherDao
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.WeatherDataModel

class ConcreteLocalSource(context: Context) : LocalSource {


    private val weatherDao : WeatherDao?
    private val alertDao : AlertDao?
    private val locationDao : LocationDao?

    // weather

    override fun insertWeatherData(weatherDataModel: WeatherDataModel) {
        weatherDao?.insertWeatherData(weatherDataModel)
    }

    override fun deleteWeatherData(weatherDataModel: WeatherDataModel) {
        weatherDao?.deleteWeatherData(weatherDataModel)
    }

    override val allStoredWeatherDataModel: LiveData<List<WeatherDataModel>>
    init {
        val dataBase = AppDataBase.getInstance(context)
        weatherDao = dataBase?.weatherDao()
        allStoredWeatherDataModel = weatherDao?.getWeatherData()!!
    }


    // alert

    override fun insertAlert(alert: Alert) {
        alertDao?.insertAlert(alert)
    }

    override fun deleteAlert(alert: Alert) {
        alertDao?.deleteAlert(alert)
    }

    override val allStoredWeatherAlerts: LiveData<List<Alert>>
    init {
        val dataBase = AppDataBase.getInstance(context)
        alertDao = dataBase?.alertDao()
        allStoredWeatherAlerts = alertDao?.getAllAlerts()!!
    }


    override fun insertLocationData(locationEntity: LocationEntity) {
        locationDao?.insertLocationData(locationEntity)
    }

    override fun deleteLocationData(locationEntity: LocationEntity) {
        locationDao?.deleteLocationData(locationEntity)
    }

    override val allStoredLocations: LiveData<List<LocationEntity>>
    init {
        val dataBase = AppDataBase.getInstance(context)
        locationDao = dataBase?.locationDao()
        allStoredLocations = locationDao?.getAllLocationData()!!
    }

}