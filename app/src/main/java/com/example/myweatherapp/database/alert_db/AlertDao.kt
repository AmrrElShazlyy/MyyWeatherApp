package com.example.myweatherapp.database.alert_db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlertDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlert(alertEntity: AlertEntity)

    @Delete
    fun deleteAlert(alertEntity: AlertEntity)

    @Query("select * from alertEntity")
    fun getAllAlerts() : LiveData<List<AlertEntity>>
}