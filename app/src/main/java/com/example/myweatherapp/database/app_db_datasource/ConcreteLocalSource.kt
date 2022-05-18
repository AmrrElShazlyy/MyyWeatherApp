package com.example.myweatherapp.database.app_db_datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.dao.AlertDao
import com.example.myweatherapp.database.dao.AlertsLocalDao
import com.example.myweatherapp.database.dao.LocationDao
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.database.dao.WeatherDao
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.pojo.WeatherDataModel
import io.reactivex.Single

class ConcreteLocalSource(context: Context) : LocalSource {


    private val weatherDao : WeatherDao?
    private val alertDao : AlertDao?
    private val locationDao : LocationDao?
    private val alertsLocalDao : AlertsLocalDao?

    init {
        val dataBase = AppDataBase.getInstance(context)
        weatherDao = dataBase?.weatherDao()
        alertDao = dataBase?.alertDao()
        locationDao = dataBase?.locationDao()
        alertsLocalDao = dataBase?.alertLocalDao()
    }

    // weather

    override fun insertWeatherData(weatherDataModel: WeatherDataModel) {
        weatherDao?.insertWeatherData(weatherDataModel)
    }

    override fun deleteWeatherData(weatherDataModel: WeatherDataModel) {
        weatherDao?.deleteWeatherData(weatherDataModel)
    }

    override suspend fun getWeatherDataModelObj(): WeatherDataModel {
        return weatherDao?.getWeatherDataModelObj()!!
    }

    override val allStoredWeatherDataModel: LiveData<List<WeatherDataModel>> = weatherDao?.getWeatherData()!!

    // alert

    override fun insertAlert(alert: Alert) {
        alertDao?.insertAlert(alert)
    }

    override fun deleteAlert(alert: Alert) {
        alertDao?.deleteAlert(alert)
    }

    override val allStoredWeatherAlerts: LiveData<List<Alert>> = alertDao?.getAllAlerts()!!


    // Location


    override fun insertLocationData(locationEntity: LocationEntity) {
        locationDao?.insertLocationData(locationEntity)
    }

    override fun deleteLocationData(locationEntity: LocationEntity) {
        locationDao?.deleteLocationData(locationEntity)
    }

    override val allStoredLocations: LiveData<List<LocationEntity>> = locationDao?.getAllLocationData()!!


    // alert local

    override fun insertAlertLocal(alertLocal: AlertLocal) {
        alertsLocalDao?.insertAlertLocal(alertLocal)
    }

    override fun deleteAlertLocal(alertLocal: AlertLocal) {
        alertsLocalDao?.deleteAlertLocal(alertLocal)
    }

    override fun getAllAlertsLocalLiveData(): LiveData<List<AlertLocal>> {
        return alertsLocalDao?.getAllAlertsLocalLiveData()!!
    }

    override fun getAllAlertsLocal(): Single<List<AlertLocal>> {
        return alertsLocalDao?.getAllAlertsLocal()!!
    }

}