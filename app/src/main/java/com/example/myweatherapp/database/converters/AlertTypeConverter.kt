package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.AlertLocal
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

        @TypeConverter
        @JvmStatic
        fun fromAlertDaysListToString(alertDays: List<String>): String {

            val gson = Gson()
            val type = object : TypeToken<List<String>>() {}.type
            return gson.toJson(alertDays, type)
        }


        @TypeConverter
        @JvmStatic
        fun fromStringToAlertDaysList(alertDaysListString: String): List<String> {

            val gson = Gson()
            val type = object : TypeToken<List<String>>() {}.type
            return gson.fromJson(alertDaysListString, type)
        }

    }
}