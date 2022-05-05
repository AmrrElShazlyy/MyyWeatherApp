package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromWeatherItemListToString(weatherList: MutableList<Weather>): String {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Weather>>() {}.type
            return gson.toJson(weatherList , type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToWeatherItemList(weatherItemListString : String) : MutableList<Weather> {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Weather>>() {}.type
            return gson.fromJson(weatherItemListString , type)
        }

    }

}