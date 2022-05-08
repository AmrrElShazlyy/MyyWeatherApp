package com.example.myweatherapp.screens.settings_screen.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.screens.dummy_test_activity.MainActivity
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModel
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModelFactory
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.util.*

class SettingsActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var locationRadioGroup: RadioGroup
    lateinit var tempRadioGroup: RadioGroup
    lateinit var windRadioGroup: RadioGroup

    lateinit var settingsViewModelFactory: SettingsViewModelFactory
    lateinit var settingsViewModel: SettingsViewModel

    lateinit var testTv : TextView

    lateinit private var  fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    var lat : Double = 0.0
    var lon : Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initUi()
        initNavDrawer()
        initLocationRadioGroup()
        initTempRadioGroup()
        initWindRadioGroup()
        //getDataFromviewModel()

        // ********* fetching location from activity direct  ***********

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //fetchLocation()
        //checkLocationPermission()
        // ********* fetching location from activity direct  ***********


    }

    fun fetchLocation() {

        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,),101)
            return
        }

        task.addOnSuccessListener {
            if (it != null){
                Toast.makeText(this,"lat : ${it.latitude} lon : ${it.longitude}",Toast.LENGTH_SHORT).show()
                lat = it.latitude
                lon = it.longitude
                testTv.text = "lat = ${lat}  lon = ${lon}"
                val sharedPreferences = this.getSharedPreferences("sharedpref",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply(){
                    putFloat(Constants.LAT_KEY , it.latitude.toFloat())
                    putFloat(Constants.LON_KEY , it.longitude.toFloat())

                }.apply()
            }
        }

    }

    fun checkLocationPermission(){

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED )
        {
            // permission is allowed
            checkGPSPermission()
        }else{
            // when permission is denied
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,),101)
            return
        }
    }

    fun checkGPSPermission(){
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(this.applicationContext)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener{ task ->
            try {
                // when the gps is on
                val response = task.getResult(ApiException::class.java)
                getCurrentLocation()

            }catch (e : ApiException){
                // when the gps is off
                e.printStackTrace()
                when(e.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        // send request to enable the gps
                        val resolveApiException = e as ResolvableApiException
                        resolveApiException.startResolutionForResult(this,200)
                    }catch (sendIntentException : IntentSender.SendIntentException){

                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // when the setting is unavailable
                    }
                }
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun getCurrentLocation(){
        fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task ->
            val location = task.getResult()
            if (location != null){
                try {

                    val geocoder = Geocoder(this, Locale.getDefault())
                    val add = geocoder.getFromLocation(location.latitude , location.longitude , 1)
                    testTv.text = "lat = ${location.latitude.toString()} lon = ${location.longitude.toString()}"

                }catch (e : IOException){

                }
            }
        }
    }

    fun initUi(){
        locationRadioGroup = findViewById(R.id.locationRadioGroup)
        tempRadioGroup = findViewById(R.id.settingsTempRadioGroup)
        windRadioGroup = findViewById(R.id.settingsWindRadioGroup)

        testTv = findViewById(R.id.settingsTestTextView)
    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.settingsDrawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this , MainActivity::class.java))
                R.id.nav_fav_screen -> Toast.makeText(this,"fav clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_alerts_screen -> Toast.makeText(this,"alerts clicked", Toast.LENGTH_SHORT).show()
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

    fun initLocationRadioGroup(){
        locationRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.gpsRadioButton -> {
                    //testTv.text = radioButton.text.toString()
                    //fetchLocation()
                    checkLocationPermission()
                    //getDataFromviewModel()
                }
                R.id.mapRadioButton -> {testTv.text = radioButton.text.toString()}
            }
        }
    }

    fun initTempRadioGroup(){
        tempRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.calvinRadioButton -> testTv.text = radioButton.text.toString()
                R.id.celsiusRadioButton -> {testTv.text = radioButton.text.toString()}
                R.id.fahrenheitRadioButton -> {testTv.text = radioButton.text.toString()}
            }
        }
    }

    fun initWindRadioGroup(){
        windRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){

                R.id.meterSecondRadioButton -> testTv.text = radioButton.text.toString()
                R.id.mileHourRadioButton -> {testTv.text = radioButton.text.toString()
                    Toast.makeText(this, "here mile per hour ", Toast.LENGTH_SHORT).show()}

            }

        }
    }

    fun getDataFromviewModel(){

        settingsViewModelFactory = SettingsViewModelFactory(Repo.getInstance(this, WeatherClient.getInstance(), ConcreteLocalSource(this)
        ),this,this)

        settingsViewModel = ViewModelProvider(this , settingsViewModelFactory).get(SettingsViewModel::class.java)
        settingsViewModel.fetchLocation()
        settingsViewModel.lat.observe(this, Observer {
            testTv.text = "lat = ${it.toString()}"
            lat = it
        })
        settingsViewModel.lon.observe(this, Observer {
            //testTv.text = "lon = ${it.toString()}"
            lon = it

        })
    }


}