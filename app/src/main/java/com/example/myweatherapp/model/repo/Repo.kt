package com.example.myweatherapp.model.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.alert_db.AlertEntity
import com.example.myweatherapp.database.LocalSource
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherEntity
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.network.RemoteSource

class Repo private constructor(var context: Context , var remoteSource: RemoteSource , var localSource: LocalSource) : RepoInterface{

    companion object{
        private var instance : Repo? = null
        fun getInstance(context: Context,remoteSource: RemoteSource,localSource: LocalSource) : Repo{
            return instance?: Repo(context,remoteSource,localSource)
        }
    }

    // from remote source

    override suspend fun fetchWeatherDataFromNetwork(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        exclude: String
    ): WeatherDataModel {

        return remoteSource.fetchWeatherDataFromNetwork(lat,lon, units, lang, exclude)
    }

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double,exclude: String): WeatherDataModel {
        return remoteSource.fetchWeatherDataFromNetwork(lat, lon,exclude)
    }

    // from local source

    // weather

    override fun insertWeatherData(weatherEntity: WeatherEntity) {
        localSource.insertWeatherData(weatherEntity)
    }

    override fun deleteWeatherData(weatherEntity: WeatherEntity) {
        localSource.deleteWeatherData(weatherEntity)
    }

    override val allStoredWeatherDataModel: LiveData<List<WeatherEntity>>
        get() = localSource.allStoredWeatherDataModel


    // alerts

    override fun insertAlert(alertEntity: AlertEntity) {
        localSource.insertAlert(alertEntity)
    }

    override fun deleteAlert(alertEntity: AlertEntity) {
        localSource.deleteAlert(alertEntity)
    }

    override val allStoredWeatherAlerts: LiveData<List<AlertEntity>>
        get() = localSource.allStoredWeatherAlerts

    override fun insertLocationData(locationEntity: LocationEntity) {
        localSource.insertLocationData(locationEntity)
    }

    override fun deleteLocationData(locationEntity: LocationEntity) {
        localSource.deleteLocationData(locationEntity)
    }

    override val allStoredLocations: LiveData<List<LocationEntity>>
        get() = localSource.allStoredLocations
}