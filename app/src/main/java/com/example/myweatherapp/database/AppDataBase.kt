package com.example.myweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.database.alert_db.AlertDao
import com.example.myweatherapp.database.alert_db.AlertEntity
import com.example.myweatherapp.database.converters.AlertTypeConverter
import com.example.myweatherapp.database.converters.CurrentTypeConverter
import com.example.myweatherapp.database.converters.DailyTypeConverter
import com.example.myweatherapp.database.location_db.LocationDao
import com.example.myweatherapp.database.location_db.LocationEntity
import com.example.myweatherapp.database.weather_db.WeatherDao
import com.example.myweatherapp.database.weather_db.WeatherEntity

@Database(entities = [WeatherEntity::class , AlertEntity::class , LocationEntity::class] , version = 1)



abstract class AppDataBase : RoomDatabase(){

    abstract fun weatherDao() : WeatherDao
    abstract fun alertDao() : AlertDao
    abstract fun locationDao() : LocationDao

    companion object{
        private var instance : AppDataBase? = null
        @Synchronized
        fun getInstance(context: Context) : AppDataBase?{
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext,AppDataBase::class.java,"Weather_App_Database")
                    .build()
            }
            return instance
        }
    }
}