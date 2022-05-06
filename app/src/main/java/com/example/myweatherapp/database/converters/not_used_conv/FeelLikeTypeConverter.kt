package com.example.myweatherapp.database.converters.not_used_conv

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.not_used_pojo.FeelsLike
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FeelLikeTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromFeelsLikeToString(feelsLike: FeelsLike): String {

            val gson = Gson()
            val type = object : TypeToken<FeelsLike>() {}.type
            return gson.toJson(feelsLike, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToFeelsLike(feelsLikeString: String): FeelsLike {

            val gson = Gson()
            val type = object : TypeToken<FeelsLike>() {}.type
            return gson.fromJson(feelsLikeString, type)
        }
    }

}