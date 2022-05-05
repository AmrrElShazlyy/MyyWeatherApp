package com.example.myweatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.weather_data_model.Alert

@Dao
interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alertEntity: AlertEntity)

    @Delete
    fun deleteAlert(alertEntity: AlertEntity)

    @Query("select * from alertEntity")
    fun getAllAlerts() : LiveData<List<AlertEntity>>
}