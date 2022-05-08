package com.example.myweatherapp.utilities

import android.app.Activity
import android.content.Context

object SharedPrefrencesHandler {

    fun saveSettingsInSharedPref(key: String, value: String , activity:Activity) {
        val sharedPreferences = activity?.getSharedPreferences("settings_shared_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply(){
            putString(key , value)
        }.apply()
    }

    fun getSettingsFromSharedPref(key: String ,defaultValue : String, activity: Activity) {
        val sharedPreferences = activity?.getSharedPreferences("settings_shared_pref", Context.MODE_PRIVATE)
        sharedPreferences.getString(key,defaultValue)
    }





}