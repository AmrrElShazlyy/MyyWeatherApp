package com.example.myweatherapp.database.location_db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myweatherapp.database.weather_db.WeatherEntity


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationData(locationEntity: LocationEntity)

    @Delete
    fun deleteLocationData(locationEntity: LocationEntity)

    @Query("select * from locationEntity")
    fun getAllLocationData() : LiveData<List<LocationEntity>>
}