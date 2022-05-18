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


    var list :List<AlertLocal> = arrayListOf()
    var delay: Long = 0
    var currentTime: Long = 0
    val repo = Repo.getInstance(context, WeatherClient.getInstance(), ConcreteLocalSource(context) )


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        getAlertsFromDb()
        getCurrentTime()
        setAlertsData()

        return Result.success()
    }
    private fun getAlertsFromDb(){

        GlobalScope.launch(Dispatchers.IO) {
            repo.localSource.getAllAlertsLocal().subscribe { alerts->
                list =alerts
                Log.e("PeriodicManager", "getAllAlertsFromDb: ${list.size}" )
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun setAlertsData() {

        val currentWeather = repo.getWeatherDataModelObj()
        if (list != null) {
            for (alert in list) {
                if (alert.alertDays.stream()
                        .anyMatch { s -> s.contains(getCurrentDay().toString()) }) {
                    if (handleAlertTime(alert.alertTime)) {
                        Log.e("PeriodicMan", "getting time of the alert ")
                        if (currentWeather.alert.isNullOrEmpty()) {
                            setOneTimeWorkManager(
                                delay,
                                alert.alertLocalId,
                                currentWeather.current?.weather?.get(0)?.description ?: "noooo descr")
                        }
                        else {
                            setOneTimeWorkManager(
                                delay,
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

        delay = alertTime - currentTime
        Log.e("periodicManager", "diff bet. time now and alert time : $delay , $alertTime" )
        return delay > 0
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