package com.example.myweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://api.openweathermap.org/"
    private const val IMG_URL="http://openweathermap.org/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

//    fun getWeatherIcon() : Retrofit {
//        return Retrofit.Builder().baseUrl(IMG_URL)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//    }

}

