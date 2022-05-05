package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Current
import com.example.myweatherapp.model.pojo.FeelsLike
import com.example.myweatherapp.model.pojo.Snow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SnowTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromSnowToString(snow: Snow): String {

            val gson = Gson()
            val type = object : TypeToken<Snow>() {}.type
            return gson.toJson(snow, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToSnow(snowString: String): Snow {

            val gson = Gson()
            val type = object : TypeToken<Snow>() {}.type
            return gson.fromJson(snowString, type)
        }
    }

}