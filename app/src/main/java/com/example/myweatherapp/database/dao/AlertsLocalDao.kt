package com.example.myweatherapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.pojo.AlertLocal
import io.reactivex.Single

@Dao
interface AlertsLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlertLocal(alertLocal: AlertLocal)

    @Delete
    fun deleteAlertLocal(alertLocal: AlertLocal)

    @Query("select * from alertsLocal")
    fun getAllAlertsLocalLiveData(): LiveData<List<AlertLocal>>

    @Query("select * from alertsLocal")
    fun getAllAlertsLocal(): Single<List<AlertLocal>>

}