package com.example.myweatherapp.screens.favourites_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.model.repo.RepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel (private val repo: RepoInterface) : ViewModel() {

    //private var _locationsEntityList : MutableLiveData<List<LocationEntity>> = MutableLiveData<List<LocationEntity>>()
    //var locationsEntityList : LiveData<List<LocationEntity>> = _locationsEntityList

    fun insertLocationEntityIntoDb(locationEntity: LocationEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertLocationData(locationEntity)
        }
    }

    fun deleteLocationEntityFromDb(locationEntity: LocationEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteLocationData(locationEntity)
        }
    }

    fun getLocationsListFromDb(): LiveData<List<LocationEntity>> {
        return repo.allStoredLocations
    }


}





