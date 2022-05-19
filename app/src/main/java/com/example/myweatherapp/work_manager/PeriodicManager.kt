package com.example.myweatherapp.work_manager

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.utilities.getCurrentDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class PeriodicManager (private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    val repo = Repo.getInstance(context, WeatherClient.getInstance(), ConcreteLocalSource(context) )
    var alertsLocalList :List<AlertLocal> = arrayListOf()
    var timeDifferenceDelay: Long = 0
    var currentTime: Long = 0


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        getAlertsFromDb()
        getCurrentTime()
        setAlertsData()

        return Result.success()
    }
    private fun getAlertsFromDb(){

        GlobalScope.launch(Dispatchers.IO) {
            repo.getAllAlertsLocal().subscribe { alerts->
                alertsLocalList =alerts
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun setAlertsData() {

        val currentWeather = repo.getWeatherDataModelObj()
        if (alertsLocalList != null) {
            for (alert in alertsLocalList) {
                if (alert.alertDays.stream()
                        .anyMatch { s -> s.contains(getCurrentDay().toString()) }) {
                    if (handleAlertTime(alert.alertTime)) {
                        Log.e("PeriodicMan", "getting time of the alert ")
                        if (currentWeather.alert.isNullOrEmpty()) {
                            setOneTimeWorkManager(
                                timeDifferenceDelay,
                                alert.alertLocalId,
                                currentWeather.current?.weather?.get(0)?.description ?: "noooo descr")
                        }
                        else {
                            setOneTimeWorkManager(
                                timeDifferenceDelay,
                                alert.alertLocalId,
                                currentWeather.alert!![0].tags[0],

                            )
                        }
                    }
                }
            }
        }

    }

    private fun handleAlertTime(alertTime: Long): Boolean {

        timeDifferenceDelay = alertTime - currentTime
        Log.e("periodicManager", "diff bet. time now and alert time : $timeDifferenceDelay , $alertTime" )
        return timeDifferenceDelay > 0
    }

    private fun getCurrentTime() {
        val calender = Calendar.getInstance()
        val hour = calender[Calendar.HOUR_OF_DAY]
        val minute = calender[Calendar.MINUTE]
        currentTime = (hour * 60).toLong()
        currentTime = ((currentTime + minute) * 60 ) - 7200
    }

    private fun setOneTimeWorkManager(delay: Long, id: Int?, description: String) {
        val data = Data.Builder()
        data.putString("description", description)
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(OneTimeWorkManager::class.java)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance().enqueueUniqueWork(
            "$id",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }



}