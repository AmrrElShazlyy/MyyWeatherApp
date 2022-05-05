package com.example.myweatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myweatherapp.model.weather_data_model.Alert
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel

@Database(entities = [WeatherEntity::class , AlertEntity::class] , version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun weatherDao() : WeatherDao
    abstract fun alertDao() : AlertDao

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