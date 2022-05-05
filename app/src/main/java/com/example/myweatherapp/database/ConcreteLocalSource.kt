package com.example.myweatherapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.alert_db.AlertDao
import com.example.myweatherapp.database.alert_db.AlertEntity
import com.example.myweatherapp.database.location_db.LocationDao
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherDao
import com.example.myweatherapp.database.weather_db.WeatherEntity

class ConcreteLocalSource(context: Context) : LocalSource {


    private val weatherDao : WeatherDao?
    private val alertDao : AlertDao?
    private val locationDao : LocationDao?

    // weather

    override fun insertWeatherData(weatherEntity: WeatherEntity) {
        weatherDao?.insertWeatherData(weatherEntity)
    }

    override fun deleteWeatherData(weatherEntity: WeatherEntity) {
        weatherDao?.deleteWeatherData(weatherEntity)
    }

    override val allStoredWeatherDataModel: LiveData<List<WeatherEntity>>
    init {
        val dataBase = AppDataBase.getInstance(context)
        weatherDao = dataBase?.weatherDao()
        allStoredWeatherDataModel = weatherDao?.getWeatherData()!!
    }


    // alert

    override fun insertAlert(alertEntity: AlertEntity) {
        alertDao?.insertAlert(alertEntity)
    }

    override fun deleteAlert(alertEntity: AlertEntity) {
        alertDao?.deleteAlert(alertEntity)
    }

    override val allStoredWeatherAlerts: LiveData<List<AlertEntity>>
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