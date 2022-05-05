package com.example.myweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherEntity : WeatherEntity)

    @Delete
    fun deleteWeatherData(weatherEntity: WeatherEntity)

    @Query("select * from weatherentity")
    fun getWeatherData() : LiveData<List<WeatherEntity>>

}