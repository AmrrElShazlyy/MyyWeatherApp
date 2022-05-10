package com.example.myweatherapp.database.app_db_datasource

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.pojo.WeatherDataModel
import io.reactivex.Single

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

    // alerts local

    fun insertAlertLocal(alertLocal: AlertLocal)
    fun deleteAlertLocal(alertLocal: AlertLocal)
    fun getAllAlertsLocalLiveData(): LiveData<List<AlertLocal>>
    fun getAllAlertsLocal(): Single<List<AlertLocal>>
}