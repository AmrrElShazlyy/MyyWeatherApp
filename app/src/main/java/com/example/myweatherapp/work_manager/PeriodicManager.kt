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


    lateinit var list :List<AlertLocal>
    var delay: Long = 0
    var timeNow: Long = 0
    val repo = Repo.getInstance(context, WeatherClient.getInstance(), ConcreteLocalSource(context) )


    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun doWork(): Result {
        setAlertsToList()
        getTimePeriod()
        getCurrentData()

        return Result.success()
    }
    private fun setAlertsToList(){

        GlobalScope.launch(Dispatchers.IO) {
            repo.localSource.getAllAlertsLocal().subscribe { alerts->
                list =alerts
                Log.e("mando", "getAllAlertsFlow: ${list.size}" )
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private suspend fun getCurrentData() {
        // ********* live dataaaaaaaa ***********
        val currentWeather = repo.getWeatherDataModelObj()
        // ********* live dataaaaaaaa ***********
        Log.e("MyPeriodicManager","getCurrentData")

        if (list != null) {
            Log.e("MyPeriodicManager","list not null")

            for (alert in list) {
                if (alert.alertDays.stream()
                        .anyMatch { s -> s.contains(getCurrentDay().toString()) }) {
                    Log.e("MyPeriodicManager", "anyMatch")

                    if (checkPeriod(alert.alertTime)) {
                        Log.e("MyPeriodicManager", "checkPeriod")

                        if (currentWeather.alert.isNullOrEmpty()) {
                            Log.e("MyPeriodicManager", "isNullOrEmpty $delay")
                            setOneTimeWorkManger(
                                delay,
                                alert.alertLocalId,
                                currentWeather.current?.weather?.get(0)?.description ?: "noooo descr")
                        }
                        else {
                            setOneTimeWorkManger(
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

    private fun checkPeriod(medTime: Long): Boolean {

        delay = medTime - timeNow
        Log.e("mando", "delay: $delay , $medTime" )
        return delay > 0
    }

    private fun getTimePeriod() {
        val calender = Calendar.getInstance()
        val hour = calender[Calendar.HOUR_OF_DAY]
        val minute = calender[Calendar.MINUTE]
        timeNow = (hour * 60).toLong()
        timeNow = ((timeNow + minute) * 60 ) - 7200
        Log.e("mando", "getTimePeriod: $timeNow" )
    }

    private fun setOneTimeWorkManger(delay: Long, id: Int?, description: String) {
        val data = Data.Builder()
        data.putString("description", description)
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(OneTimeWorkManager::class.java)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance().enqueueUniqueWork(
            "$id",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }



}