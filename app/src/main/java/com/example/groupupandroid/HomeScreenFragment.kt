package com.example.groupupandroid

import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.groupupandroid.databinding.FragmentHomeScreenBinding
import com.example.groupupandroid.databinding.NavHeaderBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import data.remote.postsExample.GroupService
import kotlinx.coroutines.launch
import models.Categories
import models.Group
import models.GroupPlacemark


class HomeScreenFragment : Fragment(), GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener{
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isMyLocationButtonEnabled = false;
        enableUserLocation()

    // TODO: Replace this with an actual import statement
//        val userGroups = createRandomGroups()
//
//        addGroupMarkersToMap(userGroups)
    }

    private var requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { actionMap ->
            when (actionMap.key) {
                android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    if (actionMap.value) {
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location : Location? ->
                                if (location!=null) {
                                    zoomToUserLocation(location)
                                }
                            }
                    } else {
                        Log.i("DEBUG", "permission denied")
                    }
                }

                android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (actionMap.value) {
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location : Location? ->
                                if (location!=null) {
                                    zoomToUserLocation(location)
                                }
                            }
                    } else {
                        Log.i("DEBUG", "permission denied")
                    }
                }
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var mMap: GoogleMap
    private lateinit var mContext: Context

    // Networking
    private lateinit var service: GroupService

    // Data
    private var groups: List<Group> = emptyList()

    // Getting xml objects
    private var binding: FragmentHomeScreenBinding? = null
    private var headerBinding: NavHeaderBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val latestLocation = locationResult.locations.last()
                zoomToUserLocation(latestLocation)

                //                for (location in locationResult.locations){
//                    // Update UI with location data
//                    // ...
//                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        headerBinding = NavHeaderBinding.inflate(layoutInflater)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

//        lifecycleScope.launch {
//            getGroups()
//        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener {location: Location? ->
//                userLocation = location
//            }
//
//        if (userLocation!= null) {
//            lifecycleScope.launch {
//                getGroups()
//            }
//        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private suspend fun getGroups(latestLocation: Location) {
        service = GroupService.create()
        groups = service.getGroups(
            lat = latestLocation.latitude.toString(),
            lon = latestLocation.longitude.toString(),
            radius = 9656.06.toString())

        addGroupMarkersToMap(groups)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding?.menuButton?.setOnClickListener {
            binding?.drawerLayout?.open()
        }

        binding?.navView?.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.HomeMenuItem -> {
                    binding?.navView?.setCheckedItem(menuItem)
                    binding?.drawerLayout?.close()
                }

                R.id.ProfileMenuItem -> {
                    binding?.navView?.setCheckedItem(menuItem)
                    binding?.drawerLayout?.close()
                }

                R.id.MyGroupsMenuItem -> {
                    binding?.navView?.setCheckedItem(menuItem)
                    binding?.drawerLayout?.close()
                }

                R.id.SignOutMenuItem -> {
                    findNavController().navigate(R.id.signOutToHomeScreen)
                }
            }
            true
        }

        binding?.locationButton?.setOnClickListener {
            enableUserLocation()
        }

    }

    private fun addGroupMarkersToMap(userGroups: List<Group>) {
        for (group in userGroups) {
            val latlng = LatLng(group.latitude,group.longitude)
            val title = group.name
            mMap.addMarker(
                MarkerOptions()
                    .position(latlng)
                    .title(title)
            )
        }
    }

    private fun createRandomGroups(): Array<Group> {
        // Creating random group #1
        val name = "Prestigious University Study Group"
        val category = Categories.academic
        val latitude = 37.4
        val longitude = -122.1
        val groupPlacemark = GroupPlacemark(
            locationName = "University Campus",
            streetNumber = "11732",
            street = "Doma Street",
            city = "Dope Town",
            state = "Maine",
            zipCode = "90015",
            country = "Malaysia"
        )
        val description = "This is a group for people that like to study!"
        val imageURL = null
        val id = null

        val group1 = Group(
            name = name,
            category = category,
            latitude = latitude,
            longitude = longitude,
            groupPlacemark = groupPlacemark,
            description = description,
            imageURL = imageURL,
            id = id
        )
        return arrayOf(group1)
    }

    private fun enableUserLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location!=null) {
                        zoomToUserLocation(location)
                    }
                }
            return
        }

        // 2. If a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale((activity as MainActivity),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale((activity as MainActivity),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            presentLocationNecessaryDialogue()
            return
        }

        // 3. Otherwise, request permission
        requestLocationPermissions.launch(locationPermissions)
    }



    private fun zoomToUserLocation(latestLocation: Location) {
        val userLocationLatLng = LatLng(latestLocation.latitude, latestLocation.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocationLatLng, 11.0F))
        mMap.addCircle(CircleOptions()
            .center(userLocationLatLng)
            .radius(9656.06)
                //TODO: Replace this with Distance object (6 miles)
            .strokeColor(Color.BLUE)
            .strokeWidth(1.0F)
            .fillColor(0x220000FF))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDrag(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    private fun presentLocationNecessaryDialogue() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
        builder.setTitle(R.string.rationale_title)
            .setMessage(R.string.rationale_desc)
            .setPositiveButton("Ok") { _, _ ->
                requestLocationPermissions.launch(locationPermissions)
            }
        builder.create().show()
    }

    companion object {
        private val locationPermissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )}
}