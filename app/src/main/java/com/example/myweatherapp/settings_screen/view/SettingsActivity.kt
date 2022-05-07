package com.example.myweatherapp.settings_screen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myweatherapp.R
import com.example.myweatherapp.dummy_test_activity.MainActivity
import com.google.android.material.navigation.NavigationView

class SettingsActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var locationRadioGroup: RadioGroup
    lateinit var tempRadioGroup: RadioGroup
    lateinit var windRadioGroup: RadioGroup

    lateinit var testTv : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initUi()
        initNavDrawer()
        initLocationRadioGroup()
        initTempRadioGroup()
        initWindRadioGroup()

    }

    fun initUi(){
        locationRadioGroup = findViewById(R.id.locationRadioGroup)
        tempRadioGroup = findViewById(R.id.settingsTempRadioGroup)
        windRadioGroup = findViewById(R.id.settingsWindRadioGroup)

        testTv = findViewById(R.id.settingsTestTextView)
    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.settingsDrawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this , MainActivity::class.java))
                R.id.nav_fav_screen -> Toast.makeText(this,"fav clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_alerts_screen -> Toast.makeText(this,"alerts clicked", Toast.LENGTH_SHORT).show()
                R.id.nav_settings_screen -> startActivity(Intent(this , SettingsActivity::class.java))

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun initLocationRadioGroup(){
        locationRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.gpsRadioButton -> testTv.text = radioButton.text.toString()
                R.id.mapRadioButton -> {testTv.text = radioButton.text.toString()}
            }
        }
    }

    fun initTempRadioGroup(){
        tempRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){
                R.id.calvinRadioButton -> testTv.text = radioButton.text.toString()
                R.id.celsiusRadioButton -> {testTv.text = radioButton.text.toString()}
                R.id.fahrenheitRadioButton -> {testTv.text = radioButton.text.toString()}
            }
        }
    }

    fun initWindRadioGroup(){
        windRadioGroup.setOnCheckedChangeListener{
                locationRadioGroup , i -> var radioButton : RadioButton = findViewById(i)
            when(radioButton.id){

                R.id.meterSecondRadioButton -> testTv.text = radioButton.text.toString()
                R.id.mileHourRadioButton -> {testTv.text = radioButton.text.toString()
                    Toast.makeText(this, "here mile per hour ", Toast.LENGTH_SHORT).show()}

            }

        }
    }


}