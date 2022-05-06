package com.example.myweatherapp.dummy_test_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.R
import com.example.myweatherapp.database.ConcreteLocalSource
import com.example.myweatherapp.database.weather_db.WeatherEntity
import com.example.myweatherapp.model.repo.Repo
import com.example.myweatherapp.model.repo.RepoInterface
import com.example.myweatherapp.model.pojo.WeatherDataModel
import com.example.myweatherapp.network.WeatherClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var tv : TextView
    lateinit var imageView: ImageView
    lateinit var repo : RepoInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("TAG", "onCreate: git test ", )

        tv = findViewById(R.id.testTextView)
        imageView = findViewById(R.id.testImageView)
        repo = Repo.getInstance(this,WeatherClient.getInstance(),ConcreteLocalSource(this))

        var _weatherData : MutableLiveData<WeatherDataModel> = MutableLiveData<WeatherDataModel>()
        var weatherData : LiveData<WeatherDataModel> = _weatherData



        //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=245934b547c45abbf4ee8472827ed844

        lifecycleScope.launch(Dispatchers.IO){
            _weatherData.postValue(repo.fetchWeatherDataFromNetwork(33.44,94.04,"minutely"))
            //_weatherData.postValue(repo.fetchWeatherDataFromNetwork(33.44,94.0,"metric","ar","minutely,hourly"))

            weatherData = _weatherData
            withContext(Dispatchers.Main){
                weatherData.observe(this@MainActivity, Observer {
                    //tv.text = it.toString()
                    //tv.text = it.current!!.weather!![0].description
                    tv.text = it.current!!.weather!![0].main
                    //tv.text = it.current?.weather?.get(0)?.description ?: ""
                    //tv.text = it.current?.weather?.get(0)?.icon

                    //Glide.with(holder.movieImageView.context).load(movieList[position].image).into(holder.movieImageView)
                    //Glide.with(imageView.context).load(it.current?.weather?.get(0)?.icon).into(imageView)

                })
            }

        }

    }
}