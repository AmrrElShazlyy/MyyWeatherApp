package com.example.myweatherapp.utilities

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myweatherapp.model.pojo.Daily
import com.example.myweatherapp.model.pojo.Hourly
import com.example.myweatherapp.model.pojo.WeatherDataModel
import org.joda.time.Days
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.TimeUnit

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

fun dateStringToLong(date: String?): Long {
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
    var milliseconds: Long = 0
    try {
        val date = simpleDateFormat.parse(date)
        milliseconds = date.time
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return milliseconds/1000
}

fun convertLongToDateAsString(dateInMillis: Long): String {
    val d = Date(dateInMillis * 1000)
    val dateFormat: DateFormat = SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH)
    return dateFormat.format(d)
}

fun convertLongToTime(time: Long): String {
    val date = Date(TimeUnit.SECONDS.toMillis(time))
    val format = SimpleDateFormat("h:mm a", Locale.ENGLISH)
    return format.format(date)
}

fun countDaysFromTo(startDate: String, endDate: String): List<String> {
    val dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy")
    val start = dateTimeFormat.parseLocalDate(startDate)
    val end = dateTimeFormat.parseLocalDate(endDate).plusDays(1)
    val myDays: MutableList<String> = ArrayList()
    val days = Days.daysBetween(LocalDate(start), LocalDate(end)).days.toLong()
    var i = 0
    while (i < days) {
        val current = start.plusDays(i)
        val date = current.toDateTimeAtStartOfDay().toString("dd-MM-yyyy")
        myDays.add(date)
        i++
    }
    return myDays
}

fun timeToSeconds(hour: Int, min: Int): Long {
    return (((hour * 60 + min) * 60) - 7200 ).toLong()
}



