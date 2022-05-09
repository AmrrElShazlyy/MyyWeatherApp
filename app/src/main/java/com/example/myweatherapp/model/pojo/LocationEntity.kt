package com.example.myweatherapp.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "locationEntity")
data class LocationEntity(@PrimaryKey var cityName: String = "",
                          var lat: Double = 0.0,
                          var lon: Double = 0.0) : Serializable{

}

data class LocationItem(var latitude : Double , var longitude: Double ){

    fun setlatitude(latitude : Double){
        this.latitude = latitude
    }
    fun getLatitue() : Double{
        return latitude
    }
}



/*
data class LocationEntity(@PrimaryKey(autoGenerate = true)
                          var id: Int = 0,
                          var title: String = "",
                          var lat: Double = 0.0,
                          var lon: Double = 0.0) {

}
 */