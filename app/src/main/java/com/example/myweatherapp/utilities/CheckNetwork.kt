package com.example.myweatherapp.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception


    // Network Check
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)

    object CheckNetwork {

        var isNetworkConnected : Boolean = false

        fun registerNetworkCallback(context: Context) {
            try {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val builder = NetworkRequest.Builder()
                connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        isNetworkConnected = true // Global Static Variable
                    }

                    override fun onLost(network: Network) {
                        isNetworkConnected = false // Global Static Variable
                    }
                }
                )
                isNetworkConnected = false
            } catch (e: Exception) {
                isNetworkConnected = false
            }
        }
    }
