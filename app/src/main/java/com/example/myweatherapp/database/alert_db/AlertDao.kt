package com.example.myweatherapp.database.alert_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.pojo.Alert

@Dao
interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alert: Alert)

    @Delete
    fun deleteAlert(alert: Alert)

    @Query("select * from alertEntity")
    fun getAllAlerts() : LiveData<List<Alert>>
}