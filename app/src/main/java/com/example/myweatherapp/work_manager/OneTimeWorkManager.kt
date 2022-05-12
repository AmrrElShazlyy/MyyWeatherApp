package com.example.myweatherapp.work_manager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.*
import com.example.myweatherapp.screens.alerts_screen.view.AlertsActivity
import com.example.myweatherapp.screens.dummy_test_activity.MainActivity
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.R

import android.graphics.drawable.BitmapDrawable




class OneTimeWorkManager(private val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context,workerParams) {

    private val CHANNEL_ID = 11
    private val channel_name = "CHANNEL_NAME"
    private val channel_description = "CHANNEL_DESCRIPTION"
    private var notificationManager: NotificationManager? = null
    var myAlertNotificationManager : MyAlertNotificationManager? = null



    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        val description = inputData.getString("description")!!
        Log.e("MyOneTimeWorkManger","doWork")
        notificationChannel()
        makeNotification(description)
        if (Settings.canDrawOverlays(context)) {
            GlobalScope.launch(Dispatchers.Main) {
                myAlertNotificationManager = MyAlertNotificationManager(context, description)
                myAlertNotificationManager!!.inflatingAlertUi()
            }
        }
        return Result.success()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun makeNotification(description: String){
        Log.e("MyOneTimeWorkManger","makeNotification")
        lateinit var builder: Notification.Builder

        val intent = Intent(applicationContext, AlertsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_dialog_alert)

        builder= Notification.Builder(applicationContext, "$CHANNEL_ID")
            .setContentText(description)
            .setContentTitle("Weather Alarm").setSmallIcon(R.drawable.ic_dialog_alert)
            .setPriority(Notification.PRIORITY_DEFAULT)
            .setStyle(
                Notification.BigTextStyle()
                    .bigText(description)
            )
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setLights(Color.RED, 3000, 3000)
            //.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.notif_ring))
            .setAutoCancel(true)
        notificationManager?.notify(1234, builder.build())

    }


    /*
    @RequiresApi(Build.VERSION_CODES.O)
private fun makeNotification(description: String, icon: String){
    Log.e("MyOneTimeWorkManger","makeNotification")
    lateinit var builder: Notification.Builder

    val intent = Intent(applicationContext, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val bitmap = BitmapFactory.decodeResource(context.resources, getIcon(icon))

    builder=Notification.Builder(applicationContext, "$CHANNEL_ID")
        .setSmallIcon(getIcon(icon))
        .setContentText(description)
        .setContentTitle("Weather Alarm")
        .setLargeIcon(bitmap)
        .setPriority(Notification.PRIORITY_DEFAULT)
        .setStyle(
            Notification.BigTextStyle()
                .bigText(description)
        )
        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        .setLights(Color.RED, 3000, 3000)
        //.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.notif_ring))
        .setAutoCancel(true)
    notificationManager?.notify(1234, builder.build())

}
     */

    private fun notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("$CHANNEL_ID", channel_name, NotificationManager.IMPORTANCE_DEFAULT)
//            val sound =
//                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + R.raw.notif_ring)
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            channel.enableVibration(true)
            //channel.setSound(sound, attributes)
            channel.description = channel_description
            notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
            Log.e("MyOneTimeWorkManger","notificationChannel")

        }
    }


}