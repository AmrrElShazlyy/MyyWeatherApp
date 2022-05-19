package com.example.myweatherapp.screens.alerts_screen.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myweatherapp.R
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.screens.alerts_screen.view_model.AlertsViewModel
import com.example.myweatherapp.screens.alerts_screen.view_model.AlertsViewModelFactory
import com.example.myweatherapp.screens.favourites_screen.view.FavouritesActivity
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.*
import com.example.myweatherapp.work_manager.PeriodicManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*
import java.util.concurrent.TimeUnit

class AlertsActivity : AppCompatActivity(), AlertOnClickListener,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var startDay = 0
    var startMonth = 0
    var startYear = 0
    var startHour = 0
    var startMinute = 0

    var endDay = 0
    var endMonth = 0
    var endYear = 0
    var endHour = 0
    var endMinute = 0
    var endFlag = false


    var lat: Double = 0.0
    var lon: Double = 0.0
    var startDateStr: String = ""
    var startDateLong: Long = 0
    var endDateStr: String = ""
    var endDateLong: Long = 0
    var alertTime: Long = 0
    var alertType = ""
    lateinit var alertDays: List<String>
    var alertlist: ArrayList<AlertLocal> = arrayListOf()


    lateinit var alertsViewModelFactory: AlertsViewModelFactory
    lateinit var alertsViewModel: AlertsViewModel

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var floatingActionButton: FloatingActionButton

    lateinit var alertsRecyclerView: RecyclerView
    lateinit var alertsAdapter: AlertsAdapter
    lateinit var alertsLayoutManager: LinearLayoutManager

    lateinit var alertDialog: Dialog
    lateinit var startAlertTimeTextView: TextView
    lateinit var endAlertTimeTextView: TextView
    lateinit var alertTypeRadioGroup: RadioGroup
    lateinit var alertRadioButton: RadioButton
    lateinit var notificationRadioButton: RadioButton
    lateinit var dialogSaveButton: Button

    var savingFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        readFromSharedPref()
        initUi()
        initViewModel()
        initNavDrawer()
        initAlertsRecyclerView()
        initAlertDialog()
        initAlertTypeRadioGroup()
        getDataFromDb()
        setOnClickListeners()


    }

    fun initUi() {
        floatingActionButton = findViewById(R.id.alertsFloatingActionButton)
    }

    fun initViewModel() {
        alertsViewModelFactory = AlertsViewModelFactory(
            Repo.getInstance(
                this,
                WeatherClient.getInstance(),
                ConcreteLocalSource(this)
            )
        )
        alertsViewModel = ViewModelProvider(this, alertsViewModelFactory).get(
            AlertsViewModel::class.java
        )
    }

    fun initNavDrawer() {

        val drawerLayout: DrawerLayout = findViewById(R.id.alertsDrawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.nav_fav_screen -> startActivity(Intent(this, FavouritesActivity::class.java))
                R.id.nav_alerts_screen -> startActivity(Intent(this, AlertsActivity::class.java))
                R.id.nav_settings_screen -> startActivity(
                    Intent(
                        this,
                        SettingsActivity::class.java
                    )
                )

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun initAlertsRecyclerView() {

        alertsRecyclerView = findViewById(R.id.alertsRecyclerView)
        alertsAdapter = AlertsAdapter(this)
        alertsLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        alertsRecyclerView.layoutManager = alertsLayoutManager
        alertsRecyclerView.adapter = alertsAdapter
    }

    override fun onItemClickListener(alertLocal: AlertLocal) {
        Toast.makeText(this, "interafce click on alert rooow deleted", Toast.LENGTH_SHORT).show()

        //alertlist.add(alertLocal)
        alertsViewModel.deleteAlert(alertLocal)
        alertsAdapter.alertLocalRecyclerList = alertlist
        alertsAdapter.notifyDataSetChanged()

    }

    fun initAlertDialog() {

        alertDialog = Dialog(this)
        alertDialog.setContentView(R.layout.alert_details_dialog_layout)
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        startAlertTimeTextView = alertDialog.findViewById(R.id.fromTextViewTime)
        endAlertTimeTextView = alertDialog.findViewById(R.id.toTextViewTime)
        alertTypeRadioGroup = alertDialog.findViewById(R.id.alertRadioGroup)
        alertRadioButton = alertDialog.findViewById(R.id.alertRadioButton)
        notificationRadioButton = alertDialog.findViewById(R.id.notificationRadioButton)
        dialogSaveButton = alertDialog.findViewById(R.id.saveAlertDetailsButton)


    }

    fun initAlertTypeRadioGroup() {
        alertTypeRadioGroup.setOnCheckedChangeListener { alertTypeRadioGroup, i ->
            var radioButton: RadioButton = alertDialog.findViewById(i)
            when (radioButton.id) {
                R.id.alertRadioButton -> {
                    alertType = "alert"
                    Log.e("alertsAct", "initTypeRadioGroup: alert")
                }
                R.id.notificationRadioButton -> {
                    alertType = "notification"
                    Log.e("alertsAct", "initTypeRadioGroup: notification")
                }

            }
        }
    }

    fun setOnClickListeners() {
        floatingActionButton.setOnClickListener {
            alertDialog.show()
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show()
        }

        startAlertTimeTextView.setOnClickListener {
            endFlag = false
            pickDate()
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show()
        }

        endAlertTimeTextView.setOnClickListener {
            endFlag = true
            pickDate()
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show()
        }

        dialogSaveButton.setOnClickListener {
            //if (getAllDataToSaveAlert() == true) {
            getAllDataToSaveAlert()
            alertDialog.dismiss()
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show()
//            }else{
//                alertDialog.dismiss()
//                Toast.makeText(this, "No Alert Is Saved ", Toast.LENGTH_LONG).show()
//
//            }
        }
    }

    private fun getDateTimeCalender() {
        val calender = Calendar.getInstance()
        day = calender.get(Calendar.DAY_OF_MONTH)
        month = calender.get(Calendar.MONTH)
        year = calender.get(Calendar.YEAR)
        hour = calender.get(Calendar.HOUR_OF_DAY)
        minute = calender.get(Calendar.MINUTE)
    }

    private fun pickDate() {

        getDateTimeCalender()
        var datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
        //  DatePickerDialog(this,this,year,month,day).show()

    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        if (endFlag == false) {
            startDay = dayOfMonth
            startMonth = monthOfYear + 1
            startYear = year
            startDateStr = "$dayOfMonth-${monthOfYear + 1}-$year"
            Log.e("alertsAck", "onDateSet: $startDateStr")
            startDateLong = dateStringToLong(startDateStr)
        } else {
            endDay = dayOfMonth
            endMonth = monthOfYear + 1
            endYear = year
            endDateStr = "$dayOfMonth-${monthOfYear + 1}-$year"
            Log.e("alertsAck", "onDateSet: date str  $endDateStr")
            endDateLong = dateStringToLong(endDateStr)
            Log.e("alertsAck", "onDateSet:date long $endDateLong")

        }

        getDateTimeCalender()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {

        if (endFlag == false) {
            startHour = hour
            startMinute = minute
            alertTime = timeToSeconds(hour, minute)
            var timeToShow = convertLongToTime(alertTime)
            startAlertTimeTextView.text = "Date : ${startDateStr} , Time : $timeToShow"
            //startAlertTimeTextView.text = "$startDay , $startMonth , $startYear --- hour : $startHour  min: ${startMinute}"
            Log.e("alertsAct", "Date : ${startDateStr} , Time : $timeToShow")
        } else {
            endHour = hour
            endMinute = minute
            //alertTime = timeToSeconds(hour,minute)
            var timeToShow = convertLongToTime(alertTime)
            endAlertTimeTextView.text = "Date : ${endDateStr} , Time : $timeToShow"
            //endAlertTimeTextView.text = "$endDay , $endMonth , $endYear --- hour : $endHour  min: ${endMinute}"
            Log.e("alertsAct", "Date : ${endDateStr} , Time : $timeToShow")
        }
    }


    private fun readFromSharedPref() {
        lat = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LAT_KEY, "noLat", this)
            .toDouble()
        lon = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LON_KEY, "noLon", this)
            .toDouble()
    }

    fun getAllDataToSaveAlert() {

        savingFlag = true
        if (startDateLong != 0L && endDateLong != 0L && alertTime != 0L && alertType != "") {
            alertDays = countDaysFromTo(startDateStr, endDateStr)
            var alertLocal = AlertLocal(
                null, lat, lon, startDateLong, endDateLong,
                alertDays, alertTime, alertType
            )
            alertsViewModel.insertAlert(alertLocal)
            Log.e("alertAct", "getAllDataToSaveAlert: after insert db")
            setPeriodicWorkManager()
            Log.e("alertAct", "getAllDataToSaveAlert: after set periodic call fun ")
            alertlist.add(alertLocal)
            alertsAdapter.alertLocalRecyclerList = alertlist
            alertsAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "there is no alert is saved ", Toast.LENGTH_LONG).show()
        }

    }

    private fun setPeriodicWorkManager() {

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            PeriodicManager::class.java, 24, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "work", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest
        )

    }

    fun getDataFromDb() {
        alertsViewModel.getAlertsLocalFromDb().observe(this, androidx.lifecycle.Observer {
            if (it != null) {
                alertsAdapter.alertLocalRecyclerList = it
                alertsAdapter.notifyDataSetChanged()
            }
        })
    }


}
