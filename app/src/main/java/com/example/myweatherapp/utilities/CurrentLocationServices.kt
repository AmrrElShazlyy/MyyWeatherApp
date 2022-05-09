package com.example.myweatherapp.utilities

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class CurrentLocationServices(var context: Context,var activity: Activity) {

    private var  fusedLocationProviderClient: FusedLocationProviderClient


    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fetchLocation()
    }

    fun fetchLocation() {

        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,),101)
            return
        }

        task.addOnSuccessListener {
            if (it != null){
                Toast.makeText(context,"lat : ${it.latitude} lon : ${it.longitude}",Toast.LENGTH_SHORT).show()
            }
        }

    }


}


/*
val sharedPreferences = activity.getSharedPreferences("sharedpref",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply(){
                    putInt(Constants.LAT_KEY , it.latitude.toInt())
                    putInt(Constants.LON_KEY , it.longitude.toInt())
                }.apply()
 */