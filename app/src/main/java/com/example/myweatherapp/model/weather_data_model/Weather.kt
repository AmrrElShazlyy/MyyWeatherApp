package com.example.myweatherapp.model.weather_data_model

import com.google.gson.annotations.SerializedName

data class Weather (@SerializedName("id")  var id: Int?,
                    @SerializedName("main")  var main: String?,
                    @SerializedName("description")  var description: String?,
                    @SerializedName("icon")  var icon: String?)


data class WeatherDataModel (    @SerializedName("lat")  var lat: Double?,
                                 @SerializedName("lon")  var lon: Double? ,
                                 @SerializedName("timezone")  var timezone: String?,
                                 @SerializedName("timezone_offset") var timezoneOffset: Int? ,
                                 @SerializedName("current")  var current: Current? ,
                                 @SerializedName("minutely")  var minutely: List<Minutely>? ,
                                 @SerializedName("hourly")  var hourly: List<Hourly>? ,
                                 @SerializedName("daily")  var daily: List<Daily>?,
                                 @SerializedName("alerts")  var alert: List<Alert>?     ){

    constructor() : this(0.0,0.0,"",0,null, emptyList(), emptyList(), emptyList(), emptyList())

}