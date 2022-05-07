package com.example.myweatherapp.database.weather_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.Current
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Hourly
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//@Entity(tableName = "weatherEntity")
//data class WeatherEntity (  @PrimaryKey(autoGenerate = true) var weatherEntityId : Int? ,
//                            @ColumnInfo(name = "lat")  var lat: Double?,
//                            @ColumnInfo(name ="lon")  var lon: Double?,
//                            @ColumnInfo(name ="timezone")  var timezone: String?,
//                            @ColumnInfo(name ="timezone_offset") var timezoneOffset: Int?,
//                            @ColumnInfo(name ="current")  var current: Current?,
//                            @ColumnInfo(name ="hourly")  var hourly: List<Hourly>?,
//                            @ColumnInfo(name ="daily")  var daily: List<Daily>?,
//                            @ColumnInfo(name ="alerts")  var alert: List<Alert>?     )

















/*
class WeatherEntity : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0

}
 */

/*
data class WeatherEntity (@ColumnInfo(name = "lat")  var lat: Double?,
                          @ColumnInfo(name ="lon")  var lon: Double?,
                          @ColumnInfo(name ="timezone")  var timezone: String?,
                          @ColumnInfo(name ="timezone_offset") var timezoneOffset: Int?,
                          @ColumnInfo(name ="current")  var current: Current?,
                          @ColumnInfo(name ="hourly")  var hourly: List<Hourly>?,
                          @ColumnInfo(name ="daily")  var daily: List<Daily>?,
                          @ColumnInfo(name ="alerts")  var alert: List<Alert>?     )
 */