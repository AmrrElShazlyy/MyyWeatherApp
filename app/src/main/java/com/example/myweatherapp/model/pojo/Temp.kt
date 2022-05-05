package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.example.myweatherapp.database.converters.TempTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(TempTypeConverter::class)
data class Temp(@SerializedName("day")  var day: Double?,
                @SerializedName("min")  var min: Double?,
                @SerializedName("max")  var max: Double?,
                @SerializedName("night")  var night: Double?,
                @SerializedName("eve")  var eve: Double?,
                @SerializedName("morn")  var morn: Double?    )