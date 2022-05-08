package com.example.myweatherapp.screens.home_screen.view_model

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.model.repo.RepoInterface

class HomeViewModelFactory(private val repo: RepoInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(repo) as T
        } else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}


