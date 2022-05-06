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





/*

data class Daily (@SerializedName("dt")  var dt: Int?,
                  @SerializedName("sunrise")  var sunrise: Int?,
                  @SerializedName("sunset")  var sunset: Int?,
                  @SerializedName("moonrise")  var moonrise: Int?,
                  @SerializedName("moonset")  var moonset: Int?,
                  @SerializedName("temp")  var temp: Temp?,
                  @SerializedName("feels_like")  var feelsLike: FeelsLike?,
                  @SerializedName("pressure")  var pressure: Int?,
                  @SerializedName("humidity")  var humidity: Int?,
                  @SerializedName("dew_point")  var dewPoint: Double?,
                  @SerializedName("wind_speed")  var windSpeed: Double?,
                  @SerializedName("wind_deg")  var windDeg: Int?,
                  @SerializedName("weather")  var weather: List<Weather>?,
                  @SerializedName("clouds")  var clouds: Int?,
                  @SerializedName("pop")  var pop: Double?,
                  @SerializedName("rain")  var rain: Double?,
                  @SerializedName("uvi")  var uvi: Double? )
 */