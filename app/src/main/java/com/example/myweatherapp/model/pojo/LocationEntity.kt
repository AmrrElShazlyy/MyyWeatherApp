package com.example.myweatherapp.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locationEntity")
data class LocationEntity(@PrimaryKey(autoGenerate = true)
                          var id: Int = 0,
                          var title: String = "",
                          var lat: Double = 0.0,
                          var lon: Double = 0.0) {

}

data class LocationItem(var latitude : Double , var longitude: Double ){

    fun setlatitude(latitude : Double){
        this.latitude = latitude
    }
}


/*
fun get1h(): Double? {
        return _1h
    }
    fun set1h(_1h: Double?) {
        this._1h = _1h
    }
 */