package com.example.myweatherapp.screens.settings_screen.view_model

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.repo.RepoInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(private val repo: RepoInterface) : ViewModel() {

    private var _lat : MutableLiveData<Double> = MutableLiveData<Double>()
    var lat : LiveData<Double> = _lat

    private var _lon : MutableLiveData<Double> = MutableLiveData<Double>()
    var lon : LiveData<Double> = _lon



}








// private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient

/*
    fun fetchLocation() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
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
                Toast.makeText(context,"lat : ${it.latitude} lon : ${it.longitude}", Toast.LENGTH_SHORT).show()
                //lat = it.latitude
                //lon = it.longitude
                //testTv.text = "lat = ${lat}  lon = ${lon}"
                viewModelScope.launch(Dispatchers.IO) {
                    _lat.postValue(it.latitude)
                    _lon.postValue(it.longitude)
                }
//                val sharedPreferences = activity.getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.apply {
//                    putFloat(Constants.LAT_KEY , it.latitude.toFloat())
//                    putFloat(Constants.LON_KEY , it.longitude.toFloat())
//
//                }.apply()
            }
        }

    }
     */