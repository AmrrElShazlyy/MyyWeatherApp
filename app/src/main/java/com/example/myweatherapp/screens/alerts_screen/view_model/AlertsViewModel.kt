package com.example.myweatherapp.screens.alerts_screen.view_model

import androidx.lifecycle.ViewModel
import com.example.myweatherapp.model.repo.RepoInterface

class AlertsViewModel(private val repo: RepoInterface) : ViewModel() {



}




/*
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
 */