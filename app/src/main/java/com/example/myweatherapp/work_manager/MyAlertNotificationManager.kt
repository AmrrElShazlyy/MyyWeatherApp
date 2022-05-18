package com.example.myweatherapp.work_manager

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import com.example.myweatherapp.R

class MyAlertNotificationManager (private val context: Context, private val description : String) {

    private var windowManager: WindowManager? = null
    lateinit var alertNotificationView: View

    lateinit var notificationDescriptionTextView: TextView
    lateinit var dismissButton: Button


    private fun initUI() {

        notificationDescriptionTextView = alertNotificationView.findViewById(R.id.alertNotificationTextView)
        dismissButton = alertNotificationView.findViewById(R.id.alertNotificationDismissButton)

        notificationDescriptionTextView.text = description
        dismissButton.setOnClickListener {
            Log.e("MyAlertManager", " dismiss ", )
            dismissAlert()
        }

    }

    fun inflatingAlertUi() {


        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        alertNotificationView = inflater.inflate(R.layout.alert_notification_layout, null)
        initUI()

        val LAYOUT_FLAG: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val layoutParams = WindowManager.LayoutParams(width, WindowManager.LayoutParams.WRAP_CONTENT, LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT)
        windowManager!!.addView(alertNotificationView, layoutParams)

    }


    private fun dismissAlert() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(
                alertNotificationView
            )
            alertNotificationView!!.invalidate()
            (alertNotificationView!!.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }


}