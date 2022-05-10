package com.example.myweatherapp.screens.alerts_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.model.repo.RepoInterface
import com.example.myweatherapp.screens.home_screen.view_model.HomeViewModel

class AlertsViewModelFactory (private val repo: RepoInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertsViewModel::class.java)){
            AlertsViewModel(repo) as T
        } else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}


