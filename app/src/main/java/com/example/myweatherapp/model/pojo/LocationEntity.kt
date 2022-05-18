package com.example.myweatherapp.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locationEntity")
data class LocationEntity(@PrimaryKey var cityName: String = "",
                          var lat: Double = 0.0,
                          var lon: Double = 0.0) : Serializable{

}
