package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Current
import com.example.myweatherapp.model.pojo.FeelsLike
import com.example.myweatherapp.model.pojo.Rain
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