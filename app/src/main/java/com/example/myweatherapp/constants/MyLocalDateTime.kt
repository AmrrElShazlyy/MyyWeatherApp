package com.example.myweatherapp.constants

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.WeatherDataModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class MyLocalDateTime {

    companion object{

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDateFromWeatherData(weatherDataModel: WeatherDataModel) : String{

            return LocalDateTime.ofEpochSecond(
                weatherDataModel.current!!.dt!!.toLong(),0,
                ZoneOffset.ofTotalSeconds(weatherDataModel.timezoneOffset!!)).toLocalDate().toString()
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDayAndDateFromWeatherDataObj(weatherDataModel: WeatherDataModel) : Pair<String,String>{

            var date = LocalDateTime.ofEpochSecond(
                weatherDataModel.current!!.dt!!.toLong(),0,
                ZoneOffset.ofTotalSeconds(weatherDataModel.timezoneOffset!!)).toLocalDate().toString()

            var day = LocalDateTime.ofEpochSecond(
                weatherDataModel.current!!.dt!!.toLong(),0,
                ZoneOffset.ofTotalSeconds(weatherDataModel.timezoneOffset!!)).dayOfWeek.toString()
            return Pair(day,date)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getTimeFromHourlyObj(hourly: Hourly) : String{

            return LocalDateTime.ofEpochSecond(
                hourly.dt!!.toLong(),0,
                ZoneOffset.UTC).toLocalTime().toString()

        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getDayFromDailyObj(daily: Daily) : String{

            return LocalDateTime.ofEpochSecond(
                daily.dt!!.toLong(),0,
                ZoneOffset.UTC).dayOfWeek.toString()

        }



    }
}