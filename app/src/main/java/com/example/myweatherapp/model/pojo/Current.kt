package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.CurrentTypeConverter
import com.google.gson.annotations.SerializedName


@TypeConverters(CurrentTypeConverter::class)
data class Current (@SerializedName("dt")  var dt: Int?,
                    @SerializedName("temp")  var temp: Double?,
                    @SerializedName("pressure")  var pressure: Int?,
                    @SerializedName("clouds")  var clouds: Int?,
                    @SerializedName("wind_speed")  var windSpeed: Double?,
                    @SerializedName("humidity")  var humidity: Int?,
                    @SerializedName("weather")  var weather: List<Weather>? )






/*
data class Current (@SerializedName("dt")  var dt: Int?,
                    @SerializedName("sunrise")  var sunrise: Int?,
                    @SerializedName("sunset")  var sunset: Int?,
                    @SerializedName("temp")  var temp: Double?,
                    @SerializedName("feels_like")  var feelsLike: Double?,
                    @SerializedName("pressure")  var pressure: Int?,
                    @SerializedName("humidity")  var humidity: Int?,
                    @SerializedName("dew_point")  var dewPoint: Double?,
                    @SerializedName("uvi")  var uvi: Double?,
                    @SerializedName("clouds")  var clouds: Int?,
                    @SerializedName("visibility") var visibility: Int?,
                    @SerializedName("wind_speed")  var windSpeed: Double?,
                    @SerializedName("wind_gust")  var windGust: Double?,
                    @SerializedName("wind_deg")  var windDeg: Int?,
                    @SerializedName("weather")  var weather: List<Weather>? )
 */