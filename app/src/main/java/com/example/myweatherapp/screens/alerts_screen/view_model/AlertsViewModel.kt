package com.example.myweatherapp.screens.alerts_screen.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.model.pojo.AlertLocal
import com.example.myweatherapp.model.repo.RepoInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class AlertsViewModel(private val repo: RepoInterface) : ViewModel() {


    fun insertAlert( alertLocal: AlertLocal) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.insertAlertLocal(alertLocal)
            } catch (e: Exception) {
                throw e
            }
            this.cancel()
        }
    }

    fun getAlertsLocalFromDb() : LiveData<List<AlertLocal>> {
        return repo.getAllAlertsLocalLiveData()
    }

    fun deleteAlert(alertLocal: AlertLocal){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAlertLocal(alertLocal)
        }
    }

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


    fun insertAlert( lat : Double , lon : Double , startDate : Long , endDate : Long , alertDays : List<String>,
                      alertTime : Long, alertType : String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var alertlocal = AlertLocal(null,lat, lon, startDate, endDate, alertDays, alertTime, alertType)
                repo.insertAlertLocal(alertlocal)
 */