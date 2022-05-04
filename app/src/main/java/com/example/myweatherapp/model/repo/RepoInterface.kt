package com.example.myweatherapp.model.repo

import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

interface RepoInterface {

    // network
    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                            lang : String , exclude : String): WeatherDataModel

    suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double): WeatherDataModel

}