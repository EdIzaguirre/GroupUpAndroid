package com.example.groupupandroid

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import data.remote.postsExample.PostResponse
import data.remote.postsExample.PostsService
import kotlinx.coroutines.launch
import models.Categories
import models.Group
import models.GroupPlacemark
import models.stockPhotoURLs

class HomeScreenFragment : Fragment(), GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener{
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings.isMyLocationButtonEnabled = false;
        enableUserLocation()

        // TODO: Replace this with an actual import statement
        val userGroups = createRandomGroups()

        addGroupMarkersToMap(userGroups)
    }

    private var requestLocationPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.forEach { actionMap ->
            when (actionMap.key) {
                android.Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    if (actionMap.value) {
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                        zoomToUserLocation()

                    } else {
                        Log.i("DEBUG", "permission denied")
                    }
                }

                android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                    if (actionMap.value) {
                        Log.i("DEBUG", "permission granted")
                        mMap.isMyLocationEnabled = true
                        zoomToUserLocation()
                    } else {
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

    // Networking
    private lateinit var service: PostsService

    // Data
    private var posts: List<PostResponse> = emptyList()

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

        lifecycleScope.launch {
            getPosts()
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    private suspend fun getPosts() {
        service = PostsService.create()
        posts = service.getPosts()
        println("Here are the posts")
        print(posts::class.simpleName)
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

    private fun addGroupMarkersToMap(userGroups: Array<Group>) {
        for (group in userGroups) {
            val latlng = group.location
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
        val name = "Prestigous Univeristy Study Group"
        val category = Categories.academic
        val location = LatLng(37.4, -122.1)
        val groupPlacemark = GroupPlacemark(
            locationName = "University Campus",
            streetNumber = "11732",
            street = "Doma Stret",
            city = "Dope Town",
            state = "Maine",
            zipCode = "90015",
            country = "Malaysia"
        )
        val description = "This is a group for people that like to study!"
        val members = 4
        val imageURL = null
        val id = null

        val group1 = Group(
            name = name,
            category = category,
            location = location,
            groupPlacemark = groupPlacemark,
            description = description,
            members = members,
            imageURL = imageURL,
            id = id
        )

        // Creating random group #2
        val name2 = "We Love Animals"
        val category2 = Categories.hobbies
        val location2 = LatLng(37.41, -122.08)
        val groupPlacemark2 = GroupPlacemark(
            locationName = "Mountain View Zoo",
            streetNumber = "1244",
            street = "Lovers Lane",
            city = "Mountain",
            state = "Florida",
            zipCode = "19395",
            country = "Denmark"
        )
        val description2 = "We love animals!"
        val members2 = 22
        val imageURL2 = stockPhotoURLs[1]
        val id2 = 12412555

        val group2 = Group(
            name = name2,
            category = category2,
            location = location2,
            groupPlacemark = groupPlacemark2,
            description = description2,
            members = members2,
            imageURL = imageURL2,
            id = id2
        )
        return arrayOf(group1, group2)
    }

    private fun enableUserLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            zoomToUserLocation()
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