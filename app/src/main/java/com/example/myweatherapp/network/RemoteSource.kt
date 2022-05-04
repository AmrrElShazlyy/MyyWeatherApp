package com.example.myweatherapp.network

import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

interface RemoteSource {

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                            lang : String , exclude : String): WeatherDataModel

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double): WeatherDataModel
}