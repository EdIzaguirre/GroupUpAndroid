package com.example.groupupandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.groupupandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Getting xml objects
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // If register button is tapped make register visible
        binding.registerButton.setOnClickListener {
            binding.registerFields.visibility = View.VISIBLE
            binding.loginFields.visibility = View.GONE
        }

        // If login button is tapped make login visible
        binding.loginButton.setOnClickListener {
            binding.registerFields.visibility = View.GONE
            binding.loginFields.visibility = View.VISIBLE
        }

        // If register button is pushed swap to maps
        binding.registerButton
    }
}