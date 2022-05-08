package com.example.myweatherapp.network

import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.model.pojo.WeatherDataModel

class WeatherClient private constructor() : RemoteSource{

    override suspend fun fetchWeatherDataFromNetwork(lat : Double , lon : Double , units : String,
                                                     lang : String , exclude : String): WeatherDataModel {
        var fetchedWeatherData : WeatherDataModel = WeatherDataModel()
        val weatherService = RetrofitHelper.makeWeatherDataApiCall().create(WeatherService::class.java)
        val response = weatherService.fetchWeatherData(lat,lon, Constants.API_KEY,units,lang,exclude)
        if (response.isSuccessful){
            fetchedWeatherData = response.body()!!
        }
        return fetchedWeatherData
    }

    override suspend fun fetchWeatherDataFromNetwork(lat: Double, lon: Double,exclude: String): WeatherDataModel {
        var fetchedWeatherData : WeatherDataModel = WeatherDataModel()
        val weatherService = RetrofitHelper.makeWeatherDataApiCall().create(WeatherService::class.java)
        val response = weatherService.fetchWeatherData(lat,lon, Constants.API_KEY,exclude)
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