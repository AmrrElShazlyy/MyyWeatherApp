package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.example.myweatherapp.database.converters.FeelLikeTypeConverter
import com.google.gson.annotations.SerializedName


@TypeConverters(FeelLikeTypeConverter::class)
data class FeelsLike(@SerializedName("day")  var day: Double?,
                     @SerializedName("night")  var night: Double?,
                     @SerializedName("eve")  var eve: Double?,
                     @SerializedName("morn")  var morn: Double?  )