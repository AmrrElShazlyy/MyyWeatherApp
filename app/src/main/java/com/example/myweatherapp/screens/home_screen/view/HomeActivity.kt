package com.example.myweatherapp.screens.home_screen.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myweatherapp.R
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.MyLocalDateTime
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.screens.dummy_test_activity.MainActivity
import com.example.myweatherapp.screens.home_screen.view_model.HomeViewModel
import com.example.myweatherapp.screens.home_screen.view_model.HomeViewModelFactory
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.screens.favourites_screen.view.FavouritesActivity
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var currentLocationTv : TextView
    lateinit var currentDateTv : TextView
    lateinit var currentIconIv : ImageView
    lateinit var currentDescrTv : TextView
    lateinit var currentTempTv : TextView
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

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory

    private var homeLat : String = ""
    private var homeLon : String = ""
    private var homeUnits : String = ""
    private var homeLanguage : String = ""


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initUI()
        initNavDrawer()
        initHourlyRecyclerView()
        initDailyRecyclerView()
        readFromSharedPref()
        getDataFromviewModel()

    }

    fun initUI(){
        currentLocationTv = findViewById(R.id.homeTextViewLocation)
        currentDateTv =  findViewById(R.id.homeTextViewDate)
        currentIconIv = findViewById(R.id.homeImageViewCurrentIcon)
        currentDescrTv = findViewById(R.id.homeTextViewCurrentDescr)
        currentTempTv = findViewById(R.id.homeTextViewCurrentTemp)
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
                R.id.nav_home_screen -> startActivity(Intent(this , HomeActivity::class.java))
                R.id.nav_fav_screen -> startActivity(Intent(this , FavouritesActivity::class.java))
                R.id.nav_alerts_screen -> Toast.makeText(this,"alerts clicked",Toast.LENGTH_SHORT).show()
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataFromviewModel(){

        homeViewModelFactory = HomeViewModelFactory(
            Repo.getInstance(this,
                WeatherClient.getInstance(),
                ConcreteLocalSource(this)
            ))
        homeViewModel = ViewModelProvider(this , homeViewModelFactory).get(HomeViewModel::class.java)

        readFromSharedPref()
        homeViewModel.getWeatherDataModelFromNetwork(homeLat,homeLon,homeUnits,homeLanguage)
        homeViewModel.weatherData.observe(this , Observer {

            hourlyAdapter.hourlyList = it.hourly!!
            hourlyAdapter.hourlyUnits = homeUnits
            hourlyAdapter.notifyDataSetChanged()

            dailyAdapter.dailyList = it.daily!!
            dailyAdapter.dailyUnits = homeUnits
            dailyAdapter.notifyDataSetChanged()

            // ************ inserting in DB (need to change primary key) *************
            //homeViewModel.insertWeatherDataModelIntoDB(it)
            // ************************************************************

            // setting current data in UI
            setCurrentDataInUi(it)

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCurrentDataInUi(weatherDataModel: WeatherDataModel){

        currentLocationTv.text = weatherDataModel.timezone

        var dayyy : String = MyLocalDateTime.getDayAndDateFromWeatherDataObj(weatherDataModel).first
        var datett : String = MyLocalDateTime.getDayAndDateFromWeatherDataObj(weatherDataModel).second
        currentDateTv.text = "$dayyy - $datett"
        currentDescrTv.text = weatherDataModel.current?.weather?.get(0)?.description ?: "emptyyy"

        // *****************   add when temp get in C or K or F  *********************
        when(homeUnits){
            Constants.myUnitStandard -> {currentTempTv.text = "${weatherDataModel.current?.temp.toString()} °K "}
            Constants.myUnitMetric -> {currentTempTv.text = "${weatherDataModel.current?.temp.toString()} ℃ "}
            Constants.myUnitImperial -> {currentTempTv.text = "${weatherDataModel.current?.temp.toString()} °F "}
        }

        var currentIcon : String = weatherDataModel.current?.weather?.get(0)?.icon!!
        var currentIconUrl : String = "${Constants.IMG_URL+currentIcon}.png"
        Glide.with(currentIconIv.context).load(currentIconUrl).into(currentIconIv)

        currentPressureTv.text = "Pressure \n ${weatherDataModel.current?.pressure.toString()} hpa"
        currentHumidityTv.text = "Humidity \n ${weatherDataModel.current?.humidity.toString()} %"
        currentCloudsTv.text = "Clouds \n ${weatherDataModel.current?.clouds.toString()} %"

        //currentWindSpeedTv.text = "Clouds \n ${weatherDataModel.current?.windSpeed.toString()} %"

        // *****************   add when temp get in m/s or mile/hour   *********************
        when(homeUnits){
            Constants.myUnitStandard -> {currentWindSpeedTv.text = "Wind Speed \n ${weatherDataModel.current?.windSpeed.toString()} m/s"}
            Constants.myUnitMetric -> {currentWindSpeedTv.text = "Wind Speed \n ${weatherDataModel.current?.windSpeed.toString()} m/s"}
            Constants.myUnitImperial -> {currentWindSpeedTv.text = "Wind Speed \n ${weatherDataModel.current?.windSpeed.toString()} mile/hour"}
        }

    }

    private fun readFromSharedPref(){
         homeLat = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LAT_KEY,"noLat",this).toString()
         homeLon = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LON_KEY,"noLon",this).toString()
         homeUnits = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.UNITS_KEY,"noUnits",this).toString()
         homeLanguage = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LANGUAGE_KEY,"noLanguage",this).toString()

    }

}

