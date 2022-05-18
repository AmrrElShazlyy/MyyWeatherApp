package com.example.myweatherapp.screens.main_activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        if (startLat.equals("noLat") ) {
            openDialog()
        }else{
            startActivity(Intent(this,HomeActivity::class.java))

        }

        //startActivity(Intent(this,SettingsActivity::class.java))

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

//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun checkDrawOverlayPermission() {
//        // Check if we already  have permission to draw over other apps
//        if (!Settings.canDrawOverlays(this)) {
//            // if not construct intent to request permission
//            val alertDialogBuilder = MaterialAlertDialogBuilder(this)
//            alertDialogBuilder.setTitle("Permission Required")
//                .setMessage("please allow overlay permission")
//                .setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
//                    val intent = Intent(
//                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + this.packageName)
//                    )
//                    // request permission via start activity for result
//                    startActivityForResult(intent, 1)
//                    //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
//                    dialog.dismiss()
//
//                }.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
//                    dialog.dismiss()
//                }.show()
//        }
//    }

}





