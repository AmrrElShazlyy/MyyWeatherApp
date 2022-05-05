package com.example.myweatherapp.model.pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.example.myweatherapp.database.converters.SnowTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(SnowTypeConverter::class)
data class Snow (@SerializedName("1h")  private var _1hS: Double?)