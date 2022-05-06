package com.example.myweatherapp.database.converters.not_used_conv

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.not_used_pojo.Snow
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