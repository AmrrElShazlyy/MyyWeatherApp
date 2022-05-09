package com.example.myweatherapp.screens.favourites_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.model.repo.RepoInterface

class FavouritesViewModelFactory   (private val repo: RepoInterface) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)){
            FavouritesViewModel(repo) as T
        } else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }

}

