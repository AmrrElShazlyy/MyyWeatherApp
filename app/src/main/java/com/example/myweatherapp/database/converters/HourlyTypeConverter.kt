package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Hourly
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HourlyTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromHourlyListToString(hourlyList: MutableList<Hourly>): String {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Hourly>>() {}.type
            return gson.toJson(hourlyList, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToHourlyList(hourlyListString: String): MutableList<Hourly> {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Hourly>>() {}.type
            return gson.fromJson(hourlyListString, type)
        }
    }
}