package com.example.myweatherapp.model.pojo.not_used_pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.not_used_conv.SnowTypeConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(SnowTypeConverter::class)
data class Snow (@SerializedName("1h")  private var _1hS: Double?)