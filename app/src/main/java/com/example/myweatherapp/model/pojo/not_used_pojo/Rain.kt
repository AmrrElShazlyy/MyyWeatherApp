package com.example.myweatherapp.model.pojo.not_used_pojo

import androidx.room.TypeConverters
import com.example.myweatherapp.database.converters.not_used_conv.RainTypeConverter
import com.google.gson.annotations.SerializedName


@TypeConverters(RainTypeConverter::class)
data class Rain (@SerializedName("1h")  private var _1h: Double?){
}