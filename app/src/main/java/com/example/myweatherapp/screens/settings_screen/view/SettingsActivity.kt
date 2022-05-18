package com.example.myweatherapp.screens.settings_screen.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.R
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.screens.alerts_screen.view.AlertsActivity
import com.example.myweatherapp.screens.favourites_screen.view.FavouritesActivity
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModel
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModelFactory
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import java.io.IOException
import java.util.*

class SettingsActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var locationRadioGroup: RadioGroup
    lateinit var tempRadioGroup: RadioGroup
    lateinit var windRadioGroup: RadioGroup
    lateinit var languageRadioGroup : RadioGroup

    lateinit var celsiusRadioButton: RadioButton
    lateinit var kalvinRadioButton: RadioButton
    lateinit var fahrenheitRadioButton: RadioButton
    lateinit var gpsRadioButton: RadioButton
    lateinit var mapRadioButton: RadioButton
    lateinit var mileHourRadioButton: RadioButton
    lateinit var meterSecondRadioButton: RadioButton
    lateinit var arRadioButton: RadioButton
    lateinit var enRadioButton: RadioButton

    lateinit var autoCompleteConstarintLayout: ConstraintLayout
    lateinit var testTv : TextView
    var startUnits : String = ""
    var startLang : String = ""
    var startLocation : String =""
    var lat : Double = 0.0
    var lon : Double = 0.0

    lateinit var settingsViewModelFactory: SettingsViewModelFactory
    lateinit var settingsViewModel: SettingsViewModel

    private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var placesClient: PlacesClient

    var myLanguageEn : String = "en"
    var myLanguageAr : String = "ar"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        checkDrawOverlayPermission()
        initViewModel()
        initUi()
        initNavDrawer()
        initLocationRadioGroup()
        initTempRadioGroup()
        initWindRadioGroup()
        initLanguageRadioGroup()

        if (!Places.isInitialized()){
            Places.initialize(applicationContext,Constants.PLACES_API_KEY)
        }
        initGooglePlaces(this)

    }

    fun initViewModel(){
        settingsViewModelFactory = SettingsViewModelFactory(Repo.getInstance(this, WeatherClient.getInstance(), ConcreteLocalSource(this) ))
        settingsViewModel = ViewModelProvider(this , settingsViewModelFactory).get(SettingsViewModel::class.java)
    }

    fun initUi(){
        locationRadioGroup = findViewById(R.id.locationRadioGroup)
        tempRadioGroup = findViewById(R.id.settingsTempRadioGroup)
        windRadioGroup = findViewById(R.id.settingsWindRadioGroup)
        languageRadioGroup = findViewById(R.id.settingsLanguageRadioGroup)
        autoCompleteConstarintLayout = findViewById(R.id.autocompleteConstraintFargment)

       initRadioButtons()

        testTv = findViewById(R.id.settingsTestTextView)
        testTv.visibility = View.GONE
    }

    fun initRadioButtons(){
        celsiusRadioButton = findViewById(R.id.celsiusRadioButton)
        kalvinRadioButton = findViewById(R.id.calvinRadioButton)
        fahrenheitRadioButton = findViewById(R.id.fahrenheitRadioButton)
        gpsRadioButton  =findViewById(R.id.gpsRadioButton)
        mapRadioButton = findViewById(R.id.mapRadioButton)
        meterSecondRadioButton = findViewById(R.id.meterSecondRadioButton)
        mileHourRadioButton = findViewById(R.id.mileHourRadioButton)
        arRadioButton = findViewById(R.id.arabicRadioButton)
        enRadioButton = findViewById(R.id.englishRadioButton)

        readFromSharedPref()

        when(startLocation){
            Constants.myLocationGps -> {
                gpsRadioButton.isChecked = true
            }
            Constants.mylocationMap -> {
                mapRadioButton.isChecked = true
            }
        }

        when(startUnits){
            Constants.myUnitStandard -> {
                kalvinRadioButton.isChecked = true
                meterSecondRadioButton.isChecked = true
            }
            Constants.myUnitMetric -> {
                celsiusRadioButton.isChecked = true
                meterSecondRadioButton.isChecked = true
            }
            Constants.myUnitImperial -> {
                fahrenheitRadioButton.isChecked = true
                mileHourRadioButton.isChecked = true
            }
        }
        when(startLang){
            Constants.myLanguageAr -> {
                arRadioButton.isChecked = true
            }
            Constants.myLanguageEn -> {
                enRadioButton.isChecked = true
            }
        }
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
                R.id.nav_home_screen -> startActivity(Intent(this , HomeActivity::class.java))
                R.id.nav_fav_screen -> startActivity(Intent(this , FavouritesActivity::class.java))
                R.id.nav_alerts_screen -> startActivity(Intent(this , AlertsActivity::class.java))
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
                    //testTv.text = alertRadioButton.text.toString()
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LOCATION_OPTION_KEY,Constants.myLocationGps,this)
                    checkLocationPermission()
                    autoCompleteConstarintLayout.visibility = View.GONE
                }
                R.id.mapRadioButton -> {
                    //testTv.text = alertRadioButton.text.toString()
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LOCATION_OPTION_KEY,Constants.mylocationMap,this)
                    autoCompleteConstarintLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    fun initTempRadioGroup(){
        tempRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.calvinRadioButton -> {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.UNITS_KEY,Constants.myUnitStandard,this)
                    testTv.text = radioButton.text.toString()
                }
                R.id.celsiusRadioButton -> {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.UNITS_KEY,Constants.myUnitMetric,this)
                    testTv.text = radioButton.text.toString()
                }
                R.id.fahrenheitRadioButton -> {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.UNITS_KEY,Constants.myUnitImperial,this)
                    testTv.text = radioButton.text.toString()
                }
            }
        }
    }

    fun initWindRadioGroup(){
        windRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){

                R.id.meterSecondRadioButton ->  {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.UNITS_KEY,Constants.myUnitMetric,this)
                    testTv.text = radioButton.text.toString()
                }
                R.id.mileHourRadioButton -> {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.UNITS_KEY,Constants.myUnitImperial,this)
                    testTv.text = radioButton.text.toString()
                    Toast.makeText(this, "here mile per hour ", Toast.LENGTH_SHORT).show()}

            }

        }
    }

    fun initLanguageRadioGroup(){
        languageRadioGroup.setOnCheckedChangeListener{
                languageRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.englishRadioButton -> {
                    testTv.text = radioButton.text.toString()
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LANGUAGE_KEY,Constants.myLanguageEn,this)
                    setAppLanguage(myLanguageEn)
                }
                R.id.arabicRadioButton -> {
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LANGUAGE_KEY,Constants.myLanguageAr,this)
                    setAppLanguage(myLanguageAr)
                    testTv.text = radioButton.text.toString()
                }
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
            val locationManager: LocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                this.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            Log.e("SetAct***", "getCurrentLocation: outside 22    234" , )
            checkGPSPermission()
        }else{
            // when permission is denied
            Log.e("SetAct***", "getCurrentLocation: outside 22    238" , )
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
        Log.e("***", "get gps: bara try", )

        result.addOnCompleteListener{ task ->
            try {
                // when the gps is on
                Log.e("***", "get gps: try gps", )
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

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    lat = locationResult.lastLocation.latitude
                    lon = locationResult.lastLocation.longitude
                    Log.e("SetAct***", "getCurrentLocation: outside 22    293" , )
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LAT_KEY,lat.toString(),this@SettingsActivity)
                    SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LON_KEY,lon.toString(),this@SettingsActivity)
                    Log.e("SetAct***", "getCurrentLocation: outside 22    295" , )
                    Log.e("***", "getCurrentLocation: lat = ${lat.toString()} lon - ${lon.toString()} ", )

                    var latSH = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LAT_KEY,"laattt" ,this@SettingsActivity)
                    var lonSH = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LON_KEY,"loonnn" ,this@SettingsActivity)

                    Log.e("***", "getCurrentLocation: latSH = ${latSH.toString()} lonSH - ${lonSH.toString()} ", )
                    // ****************** fe 7aga ghalat rag3a men shared pref ****************
                    testTv.text = "lat = ${latSH.toString()} lon = ${lonSH.toString()}"
                    fusedLocationProviderClient.removeLocationUpdates(this)
                }
            }, Looper.getMainLooper()
        )

        Log.e("***", "getCurrentLocation: outside", )

    }


    fun initGooglePlaces(activity : Activity){
        placesClient = Places.createClient(this)

        val autocompleteSupportFragment  = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteSupportFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.LAT_LNG,Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val latLng : LatLng = place.latLng
                lat = latLng.latitude
                lon = latLng.longitude
                SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LAT_KEY,lat.toString(),activity)
                SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LON_KEY,lon.toString(),activity)
                Log.i("***", "Place: ${place.latLng} ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("***", "An error occurred: $status")
            }
        })
    }

    fun setAppLanguage(language : String) {
        val config = this.resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
        this.createConfigurationContext(config)
        this.resources.updateConfiguration(config, this.resources.displayMetrics)

        var refresh = Intent(this,SettingsActivity::class.java)
        startActivity(refresh)
    }

    private fun readFromSharedPref(){
        startLocation = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LOCATION_OPTION_KEY,"noUnits",this).toString()
        startUnits = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.UNITS_KEY,"noUnits",this).toString()
        startLang = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LANGUAGE_KEY,"noLanguage",this).toString()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkDrawOverlayPermission() {
        // Check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(this)) {
            // if not construct intent to request permission
            val alertDialogBuilder = MaterialAlertDialogBuilder(this)
            alertDialogBuilder.setTitle("Permission Required")
                .setMessage("please allow overlay permission")
                .setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + this.packageName)
                    )
                    // request permission via start activity for result
                    startActivityForResult(intent, 1)
                    //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
                    dialog.dismiss()

                }.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }.show()
        }
    }





}
