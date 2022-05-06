package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Temp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TempTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromTempToString(temp: Temp): String {

            val gson = Gson()
            val type = object : TypeToken<Temp>() {}.type
            return gson.toJson(temp, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToTemp(tempString: String): Temp {

            val gson = Gson()
            val type = object : TypeToken<Temp>() {}.type
            return gson.fromJson(tempString, type)
        }
    }

}