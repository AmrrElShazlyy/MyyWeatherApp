package com.example.myweatherapp.model.repo

import android.content.Context
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel
import com.example.myweatherapp.network.RemoteSource

class Repo constructor(var context: Context , var remoteSource: RemoteSource) : RepoInterface{

    override suspend fun fetchWeatherDataFromNetwork(
        lat: Double,
        lon: Double,
        units: String,
        lang: String,
        exclude: String
    ): WeatherDataModel {

        return remoteSource.fetchWeatherDataFromNetwork(lat,lon, units, lang, exclude)
    }

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double): WeatherDataModel {
        return remoteSource.fetchWeatherDataFromNetwork(lat, lon)
    }
}