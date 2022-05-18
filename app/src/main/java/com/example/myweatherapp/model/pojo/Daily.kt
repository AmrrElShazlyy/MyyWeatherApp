package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.CurrentTypeConverter
import com.example.myweatherapp.database.converters.DailyTypeConverter
import com.google.gson.annotations.SerializedName


@TypeConverters(DailyTypeConverter::class)
data class Daily (@SerializedName("dt")  var dt: Int?,
                  @SerializedName("temp")  var temp: Temp?,
                  @SerializedName("pressure")  var pressure: Int?,
                  @SerializedName("humidity")  var humidity: Int?,
                  @SerializedName("clouds")  var clouds: Int?,
                  @SerializedName("wind_speed")  var windSpeed: Double?,
                  @SerializedName("weather")  var weather: List<Weather>? )
