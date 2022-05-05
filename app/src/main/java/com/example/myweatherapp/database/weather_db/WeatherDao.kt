package com.example.myweatherapp.database.weather_db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherEntity : WeatherEntity)

    @Delete
    fun deleteWeatherData(weatherEntity: WeatherEntity)

    @Query("select * from weatherEntity")
    fun getWeatherData() : LiveData<List<WeatherEntity>>

}