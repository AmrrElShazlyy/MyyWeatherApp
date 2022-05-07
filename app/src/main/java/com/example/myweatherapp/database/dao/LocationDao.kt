package com.example.myweatherapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.model.pojo.LocationEntity


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationData(locationEntity: LocationEntity)

    @Delete
    fun deleteLocationData(locationEntity: LocationEntity)

    @Query("select * from locationEntity")
    fun getAllLocationData() : LiveData<List<LocationEntity>>
}