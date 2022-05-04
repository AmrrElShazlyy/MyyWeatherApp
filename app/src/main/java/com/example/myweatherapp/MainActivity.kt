package com.example.myweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.model.repo.RepoInterface
import com.example.myweatherapp.model.weather_data_model.WeatherDataModel
import com.example.myweatherapp.network.WeatherClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var tv : TextView
    lateinit var repo : RepoInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("TAG", "onCreate: git test ", )

        tv = findViewById(R.id.descrTv)
        repo = Repo(this,WeatherClient.getInstance())

        var _weatherData : MutableLiveData<WeatherDataModel> = MutableLiveData<WeatherDataModel>()
        var weatherData : LiveData<WeatherDataModel> = _weatherData

        //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=245934b547c45abbf4ee8472827ed844
        lifecycleScope.launch(Dispatchers.IO){
            _weatherData.postValue(repo.fetchWeatherDataFromNetwork(33.44,94.04))
            weatherData = _weatherData
            withContext(Dispatchers.Main){
                weatherData.observe(this@MainActivity, Observer {
                    tv.text = it.toString()
                })
            }

        }

    }
}