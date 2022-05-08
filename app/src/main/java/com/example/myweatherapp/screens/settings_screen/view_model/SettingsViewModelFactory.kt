package com.example.myweatherapp.screens.settings_screen.view_model

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.model.repo.RepoInterface

class SettingsViewModelFactory (private val repo: RepoInterface , private var context: Context , private var activity: Activity) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingsViewModel::class.java)){
            SettingsViewModel(repo,context,activity) as T
        } else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}

