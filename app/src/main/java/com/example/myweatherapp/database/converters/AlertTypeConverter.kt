package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.Daily
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlertTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromAlertListToString(alertList: MutableList<Alert>): String {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Alert>>() {}.type
            return gson.toJson(alertList, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToAlertList(alertListString: String): MutableList<Alert> {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Alert>>() {}.type
            return gson.fromJson(alertListString, type)
        }
    }
}