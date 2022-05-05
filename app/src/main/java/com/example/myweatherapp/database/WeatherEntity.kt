package com.example.myweatherapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weatherEntity")
class WeatherEntity : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    var title: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0

}