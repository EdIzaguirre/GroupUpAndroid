package com.example.groupupandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.groupupandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.registerButton.setOnClickListener {
            binding.registerFields.visibility = View.VISIBLE
            binding.loginFields.visibility = View.GONE
        }

        binding.loginButton.setOnClickListener {
            binding.registerFields.visibility = View.GONE
            binding.loginFields.visibility = View.VISIBLE
        }


    }
}