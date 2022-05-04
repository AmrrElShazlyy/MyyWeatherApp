package com.example.myweatherapp.network

import com.example.myweatherapp.constants.Constants
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

class WeatherClient private constructor() : RemoteSource{

    override suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                                    lang : String , exclude : String): WeatherDataModel {
        var fetchedWeatherData : WeatherDataModel = WeatherDataModel()
        val weatherService = RetrofitHelper.getInstance().create(WeatherService::class.java)
        val response = weatherService.fetchWeatherData(lat,lon, Constants.API_KEY,units,lang,exclude)
        if (response.isSuccessful){
            fetchedWeatherData = response.body()!!
        }
        return fetchedWeatherData
    }

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double): WeatherDataModel {
        var fetchedWeatherData : WeatherDataModel = WeatherDataModel()
        val weatherService = RetrofitHelper.getInstance().create(WeatherService::class.java)
        val response = weatherService.fetchWeatherData(lat,lon, Constants.API_KEY)
        if (response.isSuccessful){
            fetchedWeatherData = response.body()!!
        }
        return fetchedWeatherData
    }

    companion object{
        private var instance : WeatherClient? = null
        @Synchronized
        fun getInstance() : WeatherClient{
            return instance?: WeatherClient()
        }
    }


}