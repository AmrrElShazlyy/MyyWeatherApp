package com.example.myweatherapp.home_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.model.repo.Repo
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


