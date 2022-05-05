package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.CurrentTypeConverter
import com.example.myweatherapp.database.converters.WeatherTypeConverter
import com.google.gson.annotations.SerializedName


@TypeConverters(WeatherTypeConverter::class)
data class Weather (    @SerializedName("id")  var id: Int?,
                        @SerializedName("main")  var main: String?,
                        @SerializedName("description")  var description: String?,
                        @SerializedName("icon")  var icon: String?)


