package com.example.myweatherapp.database.app_db_datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myweatherapp.database.dao.AlertDao
import com.example.myweatherapp.database.converters.*
import com.example.myweatherapp.database.dao.AlertsLocalDao
import com.example.myweatherapp.database.dao.LocationDao
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.database.dao.WeatherDao
import com.example.myweatherapp.model.pojo.Alert
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.pojo.WeatherDataModel

@Database(entities = [WeatherDataModel::class , Alert::class , LocationEntity::class , AlertLocal::class] , version = 1)

@TypeConverters(AlertTypeConverter::class,HourlyTypeConverter::class,CurrentTypeConverter::class,DailyTypeConverter::class
    ,TempTypeConverter::class,WeatherTypeConverter::class)

abstract class AppDataBase : RoomDatabase(){

    abstract fun weatherDao() : WeatherDao
    abstract fun alertDao() : AlertDao
    abstract fun locationDao() : LocationDao
    abstract fun alertLocalDao() : AlertsLocalDao

    companion object{
        private var instance : AppDataBase? = null
        @Synchronized
        fun getInstance(context: Context) : AppDataBase?{
            if (instance == null){
                instance = Room.databaseBuilder(context.applicationContext,
                    AppDataBase::class.java,"Weather_App_Database")
                    .build()
            }
            return instance
        }
    }
}