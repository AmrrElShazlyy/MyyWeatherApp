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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.home_screen.view.HourlyAdapter
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class FavouritesActivity : AppCompatActivity() , FavLocationOnClickListener{

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var favAutoCompleteConstraintLayout: ConstraintLayout

    lateinit var favouritesRecyclerView: RecyclerView
    lateinit var favouritesAdapter: FavouritesAdapter
    lateinit var favouritesLayoutManager: LinearLayoutManager

    lateinit var placesClient: PlacesClient

    var lat : Double = 0.0
    var lon : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        initUI()
        initNavDrawer()
        if (!Places.isInitialized()){
            Places.initialize(applicationContext,Constants.PLACES_API_KEY)
        }
        initGooglePlaces(this)
        floatingActionButton.setOnClickListener { setFloatingButtonAction() }

    }


    fun initUI(){

        floatingActionButton = findViewById(R.id.floatingActionButton)

        favAutoCompleteConstraintLayout = findViewById(R.id.favAutocompleteConstraintFargment)

        favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView)
        favouritesAdapter = FavouritesAdapter(this)
        favouritesLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        favouritesRecyclerView.layoutManager = favouritesLayoutManager
        favouritesRecyclerView.adapter = favouritesAdapter
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

    fun setFloatingButtonAction(){
        Toast.makeText(this,"floaaating" ,Toast.LENGTH_SHORT).show()
       favAutoCompleteConstraintLayout.visibility = View.VISIBLE
    }

    override fun onItemClickListener(Location: LocationEntity) {
        
        Toast.makeText(this,"interafce click on rooow" ,Toast.LENGTH_SHORT).show()

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
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LAT_KEY,lat.toString(),activity)
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LON_KEY,lon.toString(),activity)
                Log.e("***", "Place: ${place.latLng} ${place.name}, ${place.id}")
                Log.e("***", "lat fav : ${lat} lon fav  ${lon}")

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("***", "An error occurred: $status")
            }
        })
    }

}