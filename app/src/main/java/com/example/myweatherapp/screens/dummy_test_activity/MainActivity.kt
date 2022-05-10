package com.example.myweatherapp.screens.dummy_test_activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.myweatherapp.R
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.model.repo.RepoInterface
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.example.myweatherapp.utilities.Constants
import com.example.myweatherapp.utilities.SharedPrefrencesHandler

class MainActivity : AppCompatActivity() {

    lateinit var tv : TextView
    lateinit var imageView: ImageView
    lateinit var repo : RepoInterface

    lateinit var dialog: Dialog
    lateinit var gotoSettingsButton : Button

    var startLat : String = ""
    var startLon : String = ""
    var startUnits : String = ""
    var startLang : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readFromSharedPref()
        if (startLat == "" || startLon == "" || startUnits == "" || startLang == "") {
            openDialog()
        }else{
            startActivity(Intent(this,HomeActivity::class.java))
        }

    }

    fun openDialog(){

        dialog = Dialog(this)
        dialog.setContentView(R.layout.startup_dialog_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        gotoSettingsButton = dialog.findViewById(R.id.goToSettingsButton)
        gotoSettingsButton.setOnClickListener{startActivity(Intent(this,SettingsActivity::class.java))}

        dialog.show()
    }

    private fun readFromSharedPref(){
        startLat = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LAT_KEY,"noLat",this).toString()
        startLon = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LON_KEY,"noLon",this).toString()
        startUnits = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.UNITS_KEY,"noUnits",this).toString()
        startLang = SharedPrefrencesHandler.getSettingsFromSharedPref(Constants.LANGUAGE_KEY,"noLanguage",this).toString()

    }
}



/*

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
                   tv.text = it.current!!.weather!![0].description
                   //tv.text = it.current!!.weather!![0].main
                   //tv.text = it.current?.weather?.get(0)?.description ?: ""
                   //tv.text = it.current?.weather?.get(0)?.icon

                   // need casting or replace weather entity

                   //var weatherEntity : WeatherEntity = it

               //Glide.with(holder.movieImageView.context).load(movieList[position].image).into(holder.movieImageView)
                   //Glide.with(imageView.context).load(it.current?.weather?.get(0)?.icon).into(imageView)

               })
           }

       }

       */


/*


        skipButton = dialog.findViewById(R.id.dialogSkipButton) ;
        takeButton = dialog.findViewById(R.id.dialogTakeButton);
        snoozeButton = dialog.findViewById(R.id.dialogSnoozeButton) ;
        timeTextView = dialog.findViewById(R.id.dialogTimeTextView) ;
        drugNameTextView = dialog.findViewById(R.id.dialogDrugNameTextView) ;
        drugDescrTextView = dialog.findViewById(R.id.dialogDrugDescrTextView) ;
        drugIconImageView = dialog.findViewById(R.id.dialogDrugIconimageView) ;

        drugNameTextView.setText("Panadol");
        drugDescrTextView.setText("500mg");

        dialog.show();
 */