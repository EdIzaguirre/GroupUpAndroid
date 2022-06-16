package com.example.groupupandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.groupupandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Getting xml objects
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this,R.id.loginRegisterFragment)
        //Navigation.setupActionBarWithNavController(this,navController)
    }
}