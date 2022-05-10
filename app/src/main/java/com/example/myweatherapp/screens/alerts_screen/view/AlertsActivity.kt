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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.screens.favourites_screen.view.FavouritesActivity
import com.example.myweatherapp.screens.home_screen.view.DailyAdapter
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.home_screen.view.HourlyAdapter
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*

class AlertsActivity : AppCompatActivity() , AlertOnClickListener,DatePickerDialog.OnDateSetListener , TimePickerDialog.OnTimeSetListener {

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
    var type = ""


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts)

        initUi()
        initNavDrawer()
        initAlertsRecyclerView()
        initAlertDialog()
        initTypeRadioGroup()
        setOnClickListeners()


    }

    fun initUi() {

        floatingActionButton = findViewById(R.id.alertsFloatingActionButton)
    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.alertsDrawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this , HomeActivity::class.java))
                R.id.nav_fav_screen -> startActivity(Intent(this , FavouritesActivity::class.java))
                R.id.nav_alerts_screen ->startActivity(Intent(this , AlertsActivity::class.java))
                R.id.nav_settings_screen -> startActivity(Intent(this , SettingsActivity::class.java))

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun initAlertsRecyclerView(){

        alertsRecyclerView = findViewById(R.id.alertsRecyclerView)
        alertsAdapter = AlertsAdapter(this)
        alertsLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        alertsRecyclerView.layoutManager = alertsLayoutManager
        alertsRecyclerView.adapter = alertsAdapter
    }

    override fun onItemClickListener(alertLocal: AlertLocal) {
        Toast.makeText(this,"interafce click on alert rooow deleted" , Toast.LENGTH_SHORT).show()

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

    fun initTypeRadioGroup(){
        alertTypeRadioGroup.setOnCheckedChangeListener{
                alertTypeRadioGroup , i -> var radioButton : RadioButton = alertDialog.findViewById(i)
            when(radioButton.id){
                R.id.alertRadioButton -> {
                    type = "alert"
                    Log.e("alertsAct", "initTypeRadioGroup: alert", )
                }
                R.id.notificationRadioButton -> {
                    type = "notification"
                    Log.e("alertsAct", "initTypeRadioGroup: notification", )
                }

            }
        }
    }

    fun setOnClickListeners(){
        floatingActionButton.setOnClickListener{
            alertDialog.show()
            Toast.makeText(this,"FAB clicked",Toast.LENGTH_SHORT).show()
        }

        startAlertTimeTextView.setOnClickListener{
            endFlag = false
            pickDate()
            Toast.makeText(this,"FAB clicked",Toast.LENGTH_SHORT).show()
        }

        endAlertTimeTextView.setOnClickListener{
            endFlag = true
            pickDate()
            Toast.makeText(this,"FAB clicked",Toast.LENGTH_SHORT).show()
        }

        dialogSaveButton.setOnClickListener{
            Toast.makeText(this,"FAB clicked",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDateTimeCalender(){
        val calender = Calendar.getInstance()
        day = calender.get(Calendar.DAY_OF_MONTH)
        month = calender.get(Calendar.MONTH)
        year = calender.get(Calendar.YEAR)
        hour = calender.get(Calendar.HOUR)
        minute = calender.get(Calendar.MINUTE)
    }

    private fun pickDate(){
        //saveAlertButton.setOnClickListener{
            getDateTimeCalender()
            DatePickerDialog(this,this,year,month,day).show()
        //}
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {

        if (endFlag == false) {
            startDay = day
            startMonth = month + 1
            startYear = year
        }else{
            endDay = day
            endMonth = month + 1
            endYear = year
        }

        getDateTimeCalender()

        TimePickerDialog(this,this,hour,minute,true).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {

        if (endFlag == false) {
            startHour = hour
            startMinute = minute
            startAlertTimeTextView.text = "$startDay , $startMonth , $startYear --- hour : $startHour  min: ${startMinute}"
            Log.e("alertsAct", "$startDay - $startMonth , $startYear --- hour : $startHour  min: ${startMinute}" )
        }
        else{
            endHour = hour
            endMinute = minute
            endAlertTimeTextView.text = "$endDay , $endMonth , $endYear --- hour : $endHour  min: ${endMinute}"
            Log.e("alertsAct", "$endDay - $endMonth , $endYear --- hour : $endHour  min: ${endMinute}" )
        }
    }


}





















/*
 fun getTime() {
        myCalendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            timeStr = SimpleDateFormat("HH:mm").format(myCalendar.getTime())
        }
        TimePickerDialog(this, timeSetListener, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show()
    }


    fun getNewDate() {
        myCalendar = Calendar.getInstance()
        date =
            OnDateSetListener { datePicker, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                mydate = Date(year, month, day)
                updateLabel()
            }

        DatePickerDialog(this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.UK)
        dateStr = dateFormat.format(myCalendar.getTime())
        myTextView.text = " date : $dateStr  time : ${timeStr}"
    }
 */