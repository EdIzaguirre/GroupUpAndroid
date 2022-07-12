package com.example.groupupandroid

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
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
import com.example.groupupandroid.databinding.FragmentHomeScreenBinding
import com.example.groupupandroid.databinding.NavHeaderBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import java.util.jar.Manifest

class HomeScreenFragment : Fragment(), GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
//        googleMap.setOnMyLocationButtonClickListener(this)
//        googleMap.setOnMyLocationClickListener(this)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        enableUserLocation()
        zoomToUserLocation()
        mMap.uiSettings.isMyLocationButtonEnabled = false;
    }

    private var requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { actionMap ->
            when (actionMap.key) {
                android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    if (actionMap.value) {
                        // permission granted continue the normal
                        // workflow of app
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                    } else {
                        // if permission denied then check whether never
                        // ask again is selected or not by making use of
                        // !ActivityCompat.shouldShowRequest
                        // PermissionRationale(requireActivity(),
                        // Manifest.permission.CAMERA)
                        Log.i("DEBUG", "permission denied")
                    }
                }

                android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (actionMap.value) {
                        // permission granted continue the normal
                        // workflow of app
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                    } else {
                        // if permission denied then check whether never
                        // ask again is selected or not by making use of
                        // !ActivityCompat.shouldShowRequest
                        // PermissionRationale(requireActivity(),
                        // Manifest.permission.CAMERA)
                        Log.i("DEBUG", "permission denied")
                    }
                }
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private lateinit var mContext: Context
//    private lateinit var mActivity: Activity

    // Getting xml objects
    private var binding: FragmentHomeScreenBinding? = null
    private var headerBinding: NavHeaderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        headerBinding = NavHeaderBinding.inflate(layoutInflater)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding?.menuButton?.setOnClickListener {
            binding?.drawerLayout?.open()
        }

        binding?.navView?.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding?.drawerLayout?.close()
            true
        }

        binding?.locationButton?.setOnClickListener {
            enableUserLocation()
            zoomToUserLocation()
        }
    }

    private fun enableUserLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            return
        }

        // 2. If a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale((activity as MainActivity),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale((activity as MainActivity),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(mContext)
            builder.setTitle(R.string.rationale_title)
                .setMessage(R.string.rationale_desc)
                .setPositiveButton("Ok") { _, _ ->
                    requestLocationPermissions.launch(locationPermissions)
                }
            builder.create().show()
            return
        }

        // 3. Otherwise, request permission
        requestLocationPermissions.launch(locationPermissions)
    }

    private fun zoomToUserLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location!=null){
                    val userLocationLatLng = LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocationLatLng, 13.0F))
                }
            }
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

    companion object {
        /**
         * Request code for location permission request.
         *
         * @see .onRequestPermissionsResult
         */
        private val locationPermissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )}

}