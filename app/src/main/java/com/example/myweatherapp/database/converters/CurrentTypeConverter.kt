package com.example.myweatherapp.database.converters

import androidx.room.TypeConverter
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.Current
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentTypeConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromCurrentToString(current: Current): String {

            val gson = Gson()
            val type = object : TypeToken<Current>() {}.type
            return gson.toJson(current, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToCurrent(currentString: String): Current {

            val gson = Gson()
            val type = object : TypeToken<Current>() {}.type
            return gson.fromJson(currentString, type)
        }
    }

}


/*
companion object {

        @TypeConverter
        @JvmStatic
        fun fromCurrentListToString(currentList: MutableList<Current>): String {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Current>>() {}.type
            return gson.toJson(currentList, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToCurrentList(currentListString: String): MutableList<Current> {

            val gson = Gson()
            val type = object : TypeToken<MutableList<Current>>() {}.type
            return gson.fromJson(currentListString, type)
        }
    }
 */