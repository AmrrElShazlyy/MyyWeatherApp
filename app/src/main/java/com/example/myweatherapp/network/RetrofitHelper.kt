package com.example.myweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://api.openweathermap.org/"
    private const val IMG_URL="http://openweathermap.org/"

    //https://openweathermap.org/img/wn/10d.png

    fun makeWeatherDataApiCall(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getWeatherIconApiCall() : Retrofit {
        return Retrofit.Builder().baseUrl(IMG_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

}

