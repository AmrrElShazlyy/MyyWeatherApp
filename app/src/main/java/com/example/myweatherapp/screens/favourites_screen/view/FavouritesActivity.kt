package com.example.myweatherapp.screens.favourites_screen.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.R
import com.example.myweatherapp.model.pojo.LocationEntity
import com.example.myweatherapp.screens.home_screen.view.HomeActivity
import com.example.myweatherapp.screens.home_screen.view.HourlyAdapter
import com.example.myweatherapp.screens.settings_screen.view.SettingsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class FavouritesActivity : AppCompatActivity() , FavLocationOnClickListener{

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var floatingActionButton: FloatingActionButton

    lateinit var favouritesRecyclerView: RecyclerView
    lateinit var favouritesAdapter: FavouritesAdapter
    lateinit var favouritesLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        initUI()
        initNavDrawer()
        floatingActionButton.setOnClickListener { setFloatingButtonAction() }

    }


    fun initUI(){

        floatingActionButton = findViewById(R.id.floatingActionButton)

        favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView)
        favouritesAdapter = FavouritesAdapter(this)
        favouritesLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        favouritesRecyclerView.layoutManager = favouritesLayoutManager
        favouritesRecyclerView.adapter = favouritesAdapter
    }

    fun initNavDrawer(){

        val drawerLayout : DrawerLayout = findViewById(R.id.favDrawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){

                //Toast.makeText(this,"home clicked",Toast.LENGTH_SHORT).show()
                R.id.nav_home_screen -> startActivity(Intent(this , HomeActivity::class.java))
                R.id.nav_fav_screen -> startActivity(Intent(this , FavouritesActivity::class.java))
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

    fun setFloatingButtonAction(){
        Toast.makeText(this,"floaaating" ,Toast.LENGTH_SHORT).show()
    }

    override fun onItemClickListener(Location: LocationEntity) {
        
        Toast.makeText(this,"interafce click on rooow" ,Toast.LENGTH_SHORT).show()

    }

}