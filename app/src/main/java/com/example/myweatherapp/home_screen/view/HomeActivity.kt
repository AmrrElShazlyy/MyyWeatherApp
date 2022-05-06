package com.example.myweatherapp.home_screen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.adapters.DailyAdapter
import com.example.myweatherapp.adapters.HourlyAdapter
import com.example.myweatherapp.dummy_test_activity.MainActivity
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var locationTv : TextView
    lateinit var dateTv : TextView
    lateinit var currentIconIv : ImageView
    lateinit var currentDescrTv : TextView
    lateinit var currentPressureTv : TextView
    lateinit var currentHumidityTv : TextView
    lateinit var currentCloudsTv : TextView
    lateinit var currentWindSpeedTv : TextView

    lateinit var hourlyRecyclerView: RecyclerView
    lateinit var hourlyAdapter: HourlyAdapter
    lateinit var hourlyLayoutManager: LinearLayoutManager

    lateinit var dailyRecyclerView: RecyclerView
    lateinit var dailyAdapter: DailyAdapter
    lateinit var dailyLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        initUI()
        initNavDrawer()
        initHourlyRecyclerView()
        initDailyRecyclerView()

    }

    fun initUI(){
        locationTv = findViewById(R.id.homeTextViewLocation)
        dateTv =  findViewById(R.id.homeTextViewDate)
        currentIconIv = findViewById(R.id.homeImageViewCurrentIcon)
        currentDescrTv = findViewById(R.id.homeTextViewCurrentDescr)
        currentPressureTv = findViewById(R.id.homeTextViewCurrentPressure)
        currentHumidityTv = findViewById(R.id.homeTextViewCurrentHumidity)
        currentCloudsTv = findViewById(R.id.homeTextViewCurrentClouds)
        currentWindSpeedTv = findViewById(R.id.homeTextViewCurrentWindSpeed)
    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this , MainActivity::class.java))
                R.id.nav_fav_screen -> Toast.makeText(this,"fav clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_alerts_screen -> Toast.makeText(this,"alerts clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_settings_screen -> Toast.makeText(this,"settings clicked",Toast.LENGTH_SHORT).show()

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

    fun initHourlyRecyclerView(){

        hourlyRecyclerView = findViewById(R.id.hourlyRecyclerView)
        hourlyAdapter = HourlyAdapter()
        hourlyLayoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        hourlyRecyclerView.layoutManager = hourlyLayoutManager
        hourlyRecyclerView.adapter = hourlyAdapter
    }

    fun initDailyRecyclerView(){

        dailyRecyclerView = findViewById(R.id.dailyRecyclerview)
        dailyAdapter = DailyAdapter()
        dailyLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        dailyRecyclerView.layoutManager = dailyLayoutManager
        dailyRecyclerView.adapter = dailyAdapter
    }

}

