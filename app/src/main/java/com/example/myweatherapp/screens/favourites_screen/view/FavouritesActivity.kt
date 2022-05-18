package com.example.myweatherapp.screens.favourites_screen.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.database.app_db_datasource.ConcreteLocalSource
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.network.WeatherClient
import com.example.myweatherapp.screens.alerts_screen.view.AlertsActivity
import com.example.myweatherapp.screens.favourites_screen.view_model.FavouritesViewModel
import com.example.myweatherapp.screens.favourites_screen.view_model.FavouritesViewModelFactory
import com.example.myweatherapp.screens.google_places_screen.googlePlacesActivity
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.home_screen.view.HourlyAdapter
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModel
import com.example.myweatherapp.screens.settings_screen.view_model.SettingsViewModelFactory
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import com.example.myweatherapp.utilities.isNetworkAvailable
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class FavouritesActivity : AppCompatActivity() , FavLocationOnClickListener ,Serializable{

    lateinit var favouritesViewModelFactory: FavouritesViewModelFactory
    lateinit var favouritesViewModel: FavouritesViewModel

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var favAutoCompleteConstraintLayout: ConstraintLayout

    lateinit var favouritesRecyclerView: RecyclerView
    lateinit var favouritesAdapter: FavouritesAdapter
    lateinit var favouritesLayoutManager: LinearLayoutManager

    lateinit var placesClient: PlacesClient

    var lat : Double = 0.0
    var lon : Double = 0.0
    var cityName : String = ""
    var locationEntityList : ArrayList<LocationEntity> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        initUI()
        initViewModel()
        initRecyclerView()
        initNavDrawer()
        if (!Places.isInitialized()){
            Places.initialize(applicationContext,Constants.PLACES_API_KEY)
        }
        getLocationsListFromDb()
//        initGooglePlaces(this)
        floatingActionButton.setOnClickListener { setFloatingButtonAction() }

    }


    fun initViewModel(){
        favouritesViewModelFactory = FavouritesViewModelFactory(Repo.getInstance(this, WeatherClient.getInstance(), ConcreteLocalSource(this) ))
        favouritesViewModel = ViewModelProvider(this , favouritesViewModelFactory).get(FavouritesViewModel::class.java)
    }

    fun initUI(){
        floatingActionButton = findViewById(R.id.floatingActionButton)
        favAutoCompleteConstraintLayout = findViewById(R.id.favAutocompleteConstraintFargment)
    }

    fun initRecyclerView(){
        favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView)
        favouritesAdapter = FavouritesAdapter(this)
        favouritesLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        favouritesRecyclerView.layoutManager = favouritesLayoutManager
        favouritesRecyclerView.adapter = favouritesAdapter


    }

    override fun deleteLocationFromDb(locationEntity: LocationEntity) {
        locationEntityList.remove(locationEntity)
        favouritesAdapter.locationEntityListRecycler = locationEntityList
        favouritesAdapter.notifyDataSetChanged()
        favouritesViewModel.deleteLocationEntityFromDb(locationEntity)
    }

    override fun onItemClickListener(locationEntity: LocationEntity) {

        if (isNetworkAvailable(this)) {
            Toast.makeText(this, "interafce click on rooow", Toast.LENGTH_SHORT).show()
            var intentToHome = Intent(this, HomeActivity::class.java)
            intentToHome.putExtra(Constants.INTENT_FROM_FAV_KEY, locationEntity)
            intentToHome.putExtra(Constants.FAV_FLAG, true)
            Log.e("favAct**", "onItemClickListener: " + locationEntity.lat.toString())
            Log.e("favAct**", "onItemClickListener: " + locationEntity.lon.toString())
            Log.e("favAct**", "onItemClickListener: " + locationEntity.cityName.toString())
            startActivity(intentToHome)
        }else{
            Toast.makeText(this, "you must be connected to network", Toast.LENGTH_LONG).show()

        }

    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.favDrawerLayout)
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

    fun setFloatingButtonAction(){
        Toast.makeText(this,"floaaating" ,Toast.LENGTH_SHORT).show()

        initGooglePlaces(this)
        favAutoCompleteConstraintLayout.visibility = View.VISIBLE


        /*

        var googlePlacesIntent = Intent(this , googlePlacesActivity::class.java)
        googlePlacesIntent.putExtra(Constants.INTENT_FROM_FAV_KEY,Constants.FAV_FLAG)
        startActivity(googlePlacesIntent)

        var locationEntity = intent.getSerializableExtra(Constants.INTENT_GOOGLE_PLACES_KEY) as LocationEntity
        locationEntityList.add(locationEntity)
        Log.e("favAct**", "setFloatingButtonAction: ${locationEntity.cityName}")
        favouritesAdapter.locationEntityListRecycler = locationEntityList
        favouritesAdapter.notifyDataSetChanged()

        */

    }


    fun initGooglePlaces(activity : Activity){
        placesClient = Places.createClient(this)

        val autocompleteSupportFragment  = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteSupportFragment.setPlaceFields(listOf(
            Place.Field.ID, Place.Field.LAT_LNG,
            Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val latLng : LatLng = place.latLng
                lat = latLng.latitude
                lon = latLng.longitude
                cityName = place.name
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LAT_KEY,lat.toString(),activity)
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LON_KEY,lon.toString(),activity)
                Log.e("***", "Place: ${place.latLng} ${place.name}, ${place.id}")
                Log.e("***", "lat fav : ${lat} lon fav  ${lon}")

                var locationEntity = LocationEntity(cityName,lat,lon)
                locationEntityList.add(locationEntity)
                favouritesAdapter.locationEntityListRecycler = locationEntityList
                favouritesAdapter.notifyDataSetChanged()
                favouritesViewModel.insertLocationEntityIntoDb(locationEntity)

            }
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("***", "An error occurred: $status")
            }
        })

    }

    private fun getLocationsListFromDb(){
        favouritesViewModel.getLocationsListFromDb().observe(this, Observer {
            if (it != null){
                favouritesAdapter.locationEntityListRecycler = it
                favouritesAdapter.notifyDataSetChanged()
            }
        })
    }



}
