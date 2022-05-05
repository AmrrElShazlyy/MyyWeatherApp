package com.example.myweatherapp.network

import com.example.myweatherapp.model.pojo.WeatherDataModel

interface RemoteSource {

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                            lang : String , exclude : String): WeatherDataModel

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , exclude: String): WeatherDataModel
}