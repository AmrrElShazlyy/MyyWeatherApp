package com.example.myweatherapp.database.converters.not_used_conv

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.not_used_pojo.Rain
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RainTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromRainToString(rain: Rain): String {

            val gson = Gson()
            val type = object : TypeToken<Rain>() {}.type
            return gson.toJson(rain, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToRain(rainString: String): Rain {

            val gson = Gson()
            val type = object : TypeToken<Rain>() {}.type
            return gson.fromJson(rainString, type)
        }
    }

}