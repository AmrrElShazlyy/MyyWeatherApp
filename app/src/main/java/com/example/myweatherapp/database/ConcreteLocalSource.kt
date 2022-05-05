package com.example.myweatherapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.model.weather_data_model.Alert
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

class ConcreteLocalSource(context: Context) : LocalSource {


    private val weatherDao : WeatherDao?
    private val alertDao : AlertDao?

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
}