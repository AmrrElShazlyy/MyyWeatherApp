package com.example.myweatherapp.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationEntity")
class LocationEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
}