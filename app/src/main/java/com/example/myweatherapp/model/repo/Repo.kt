package com.example.myweatherapp.model.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.myweatherapp.database.app_db_datasource.LocalSource
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.pojo.Alert
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

    override fun saveSettingsInSharedPref() {
//        val sharedPreferences = activity?.getSharedPreferences("sharedpref",Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.apply(){
//            putFloat(Constants.LAT_KEY , it.latitude.toFloat())
//            putFloat(Constants.LON_KEY , it.longitude.toFloat())
//
//        }.apply()
    }

    override fun getSettingsFromSharedPref() {
        TODO("Not yet implemented")
    }

//    override fun initLanguage() {
//        val config = context.resources.configuration
//        val locale = Locale(language.string)
//        Locale.setDefault(locale)
//        config.setLocale(locale)
//        context.createConfigurationContext(config)
//        context.resources.updateConfiguration(config, context.resources.displayMetrics)
//    }
}