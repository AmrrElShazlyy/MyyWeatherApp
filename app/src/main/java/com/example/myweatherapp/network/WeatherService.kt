package com.example.myweatherapp.network

import com.example.myweatherapp.model.pojo.WeatherDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/onecall?")
    suspend fun fetchWeatherData(@Query("lat")lat:Double,
                                 @Query("lon")lon:Double,
                                 @Query("appid")appid:String,
                                 @Query("units")units:String,
                                 @Query("lang")lang:String,
                                 @Query("exclude")exclude:String )
            : Response<WeatherDataModel>

    @GET("data/2.5/onecall?")
    suspend fun fetchWeatherData(@Query("lat")lat:Double,
                                 @Query("lon")lon:Double,
                                 @Query("appid")appid:String,@Query("exclude")exclude:String )
            : Response<WeatherDataModel>

    @GET("img/wn/10d.png")
    //******************** change this method ******************
    suspend fun fetchWeatherIcon(@Query("lat")lat:Double,
                                 @Query("lon")lon:Double,
                                 @Query("appid")appid:String,@Query("exclude")exclude:String )
            : Response<WeatherDataModel>
}


//http://openweathermap.org/img/wn/10d@2x.png