package com.example.myweatherapp.model.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.app_db_datasource.LocalSource
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.network.RemoteSource
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import io.reactivex.Single

class Repo private constructor(var context: Context , var remoteSource: RemoteSource , var localSource: LocalSource) : RepoInterface{

    companion object{
        private var instance : Repo? = null
        fun getInstance(context: Context,remoteSource: RemoteSource,localSource: LocalSource) : Repo{
            return instance?: Repo(context,remoteSource,localSource)
        }
    }

    // from remote source

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double, units: String, lang: String
                                                     , exclude: String): WeatherDataModel {
        return remoteSource.fetchWeatherDataFromNetwork(lat,lon, units, lang, exclude)
    }

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double,exclude: String): WeatherDataModel {
        return remoteSource.fetchWeatherDataFromNetwork(lat, lon,exclude)
    }

    // from local source

    // weather

    override fun insertWeatherData(weatherDataModel: WeatherDataModel) {
        localSource.insertWeatherData(weatherDataModel)
    }

    override fun deleteWeatherData(weatherDataModel: WeatherDataModel) {
        localSource.deleteWeatherData(weatherDataModel)
    }

    override suspend fun getWeatherDataModelObj(): WeatherDataModel {
        return localSource.getWeatherDataModelObj()
    }

    override val allStoredWeatherDataModel: LiveData<List<WeatherDataModel>>
        get() = localSource.allStoredWeatherDataModel


    // alerts

    override fun insertAlert(alert: Alert) {
        localSource.insertAlert(alert)
    }

    override fun deleteAlert(alert: Alert) {
        localSource.deleteAlert(alert)
    }

    override val allStoredWeatherAlerts: LiveData<List<Alert>>
        get() = localSource.allStoredWeatherAlerts

    override fun insertLocationData(locationEntity: LocationEntity) {
        localSource.insertLocationData(locationEntity)
    }

    override fun deleteLocationData(locationEntity: LocationEntity) {
        localSource.deleteLocationData(locationEntity)
    }

    override val allStoredLocations: LiveData<List<LocationEntity>>
        get() = localSource.allStoredLocations

    override fun insertAlertLocal(alertLocal: AlertLocal) {
        localSource.insertAlertLocal(alertLocal)
    }

    override fun deleteAlertLocal(alertLocal: AlertLocal) {
        localSource.deleteAlertLocal(alertLocal)
    }

    override fun getAllAlertsLocalLiveData(): LiveData<List<AlertLocal>> {
        return localSource.getAllAlertsLocalLiveData()
    }

    override fun getAllAlertsLocal(): Single<List<AlertLocal>> {
        return localSource.getAllAlertsLocal()
    }


}

