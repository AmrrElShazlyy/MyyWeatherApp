package com.example.myweatherapp.screens.google_places_screen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.screens.favourites_screen.view.FavouritesActivity
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.Constants
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.io.Serializable

class googlePlacesActivity : AppCompatActivity() , Serializable {

    lateinit var placesClient: PlacesClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_places)

        if (!Places.isInitialized()){
            Places.initialize(applicationContext, Constants.PLACES_API_KEY)
        }
        initGooglePlaces(this)

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
                var lat = latLng.latitude
                var lon = latLng.longitude
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LAT_KEY,lat.toString(),activity)
                //SharedPrefrencesHandler.saveSettingsInSharedPref(Constants.LON_KEY,lon.toString(),activity)
                Log.e("gpa**", "Place: ${place.latLng} ${place.name}, ${place.id}")
                Log.e("gpa**", "lat fav : ${lat} lon fav  ${lon}")

                var locationEntity = LocationEntity(place.name,place.latLng.latitude,place.latLng.longitude)

                var comingIntent : String = intent.getStringExtra(Constants.INTENT_FROM_FAV_KEY) ?: ""
                if (comingIntent.equals(Constants.FAV_FLAG)){
                    var backToFavIntent = Intent(this@googlePlacesActivity,FavouritesActivity::class.java)
                    backToFavIntent.putExtra(Constants.INTENT_GOOGLE_PLACES_KEY,locationEntity)
                    startActivity(backToFavIntent)
                }else{
                    startActivity(Intent(this@googlePlacesActivity,SettingsActivity::class.java))
                }

            }
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("***", "An error occurred: $status")
            }
        })

    }
}