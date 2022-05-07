package com.example.myweatherapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.pojo.WeatherDataModel

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(weatherDataModel : WeatherDataModel)

    @Delete
    fun deleteWeatherData(weatherDataModel: WeatherDataModel)

    @Query("select * from weatherEntity")
    fun getWeatherData() : LiveData<List<WeatherDataModel>>

}