package com.example.myweatherapp.screens.home_screen.view_model

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.model.repo.RepoInterface
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.SharedPrefrencesHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: RepoInterface) : ViewModel(){

    private var _weatherData : MutableLiveData<WeatherDataModel> = MutableLiveData<WeatherDataModel>()
    var weatherData : LiveData<WeatherDataModel> = _weatherData

    fun getWeatherDataModelFromNetwork(lat : String , lon :String , units : String , language : String){
        viewModelScope.launch(Dispatchers.IO) {

            //var lat = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LAT_KEY,"laaat",)
            //val sharedPreferences = getApplication.getSharedPreferences("settings_shared_pref", Context.MODE_PRIVATE)

            _weatherData.postValue(repo.fetchWeatherDataFromNetwork(lat.toDouble(),lon.toDouble(),units,language,"minutely"))
            //_weatherData.postValue(repo.fetchWeatherDataFromNetwork(33.44,94.0,"metric","ar","minutely,hourly"))
            weatherData = _weatherData
        }
    }

    fun insertWeatherDataModelIntoDB(weatherDataModel: WeatherDataModel){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertWeatherData(weatherDataModel)

        }
    }

}

