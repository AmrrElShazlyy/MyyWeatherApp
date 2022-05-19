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

