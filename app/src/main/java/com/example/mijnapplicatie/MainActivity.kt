package com.example.mijnapplicatie

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_home.*

//Hoe speel ik muziek in een app? https://www.youtube.com/watch?v=ZokSzMuWU3Q

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        val musicSwitch = findViewById<Switch>(R.id.musicSwitch)

        val mediaPlayer = MediaPlayer.create(this,R.raw.gamesong)

        musicSwitch?.setOnCheckedChangeListener{ _ , isChecked ->
            if (isChecked) mediaPlayer.start() else mediaPlayer.pause()

        }



        //mediaPlayer.start()

/*
        musicSwitch?.setOnCheckedChangeListener{ _ , isChecked ->
            val message = if (isChecked) "Switch1:ON" else "Switch1:OFF"
            Toast.makeText(this@MainActivity, message,
                Toast.LENGTH_SHORT).show()
            Log.i(TAG, "TOGGLESWITCH " + message)
        }*/
    }

}