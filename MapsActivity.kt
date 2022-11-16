package com.example.showandtell1

import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.showandtell1.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var latitude:Double=0.toDouble()
    private var longitude:Double=0.toDouble()

    private lateinit var mLastLocation:Location
    private var mMarker: Marker?=null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            checkLocationPermiission()
        }
        buildLocationRequest()
        buildLocationCallBack()

        fusedLocationProviderClient = LocationService.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest.locationCallback, Looper.myLooper())

        bottom_navigation_view.setOnNavigationItemReselectedListener{
            when(item.itemId){
                hospital ->nearByPlace("hospital")
                hospital ->nearByPlace("clinic")
            }
        }
    }

    private fun buildLocationCallBack(){
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult?){
                mLastLocation = p0!!.locations.get(p0!!.locations.size-1)//last location
            }

            if(mMarker !=null){
                mMarker!!.remove()
            }

            latitude = mLastLocation.latitude
            longitude = mLastLocation.longitude

            val latLng = LatLng(latitude,longitude)
            val markerOptions = MarkerOptions().position(latLng).title("Current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            mMarker = mMap!!.addMarker(markerOptions)

            mMap!!.moveCamera(CameraUpdateFacotry.newLatLng(latLng))
            mMap!!.animateCamera(CameraUpdateFactory.zoomBy(8f))


        }
    }

    private fun buildLocationCallBack(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun buildLocationRequest(){

    }

    private fun checkLocationPermission(){

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}