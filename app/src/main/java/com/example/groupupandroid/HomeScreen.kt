package com.example.groupupandroid

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.groupupandroid.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var navController: Any
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var appBarConfiguration: Configuration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

//        this.navController = navHostFragment.navController
//
//        binding.navView.setupWithNavController(this.navController)
//
//        this.appBarConfiguration =
//           AppBarConfiguration(binding.navView.menu, binding.drawerLayout)
//         setupActionBarWithNavController(this.navController, appBarConfiguration)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

//    override fun onSupportNavigateUp(): Boolean =
//       (this::navController.isInitialized &&
//         this.navController.navigateUp(this.appBarConfiguration)
//       ) || super.onSupportNavigateUp()
}