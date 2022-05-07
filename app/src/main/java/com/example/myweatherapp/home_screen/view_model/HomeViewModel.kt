package com.example.myweatherapp.home_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.model.repo.RepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repo: RepoInterface) : ViewModel(){

    private var _weatherData : MutableLiveData<WeatherDataModel> = MutableLiveData<WeatherDataModel>()
    var weatherData : LiveData<WeatherDataModel> = _weatherData

    fun getWeatherDataModelFromNetwork(){
        viewModelScope.launch(Dispatchers.IO) {

            _weatherData.postValue(repo.fetchWeatherDataFromNetwork(33.44,94.04,"minutely"))
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

