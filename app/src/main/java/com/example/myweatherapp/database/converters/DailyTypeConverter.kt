package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailyTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromDailyListToString(dailyList: MutableList<Daily>): String {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Daily>>() {}.type
            return gson.toJson(dailyList, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToDailyList(dailyListString: String): MutableList<Daily> {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Daily>>() {}.type
            return gson.fromJson(dailyListString, type)
        }
    }

}