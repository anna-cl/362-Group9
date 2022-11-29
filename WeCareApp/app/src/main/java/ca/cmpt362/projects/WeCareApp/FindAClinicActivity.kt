package ca.cmpt362.projects.weCareApp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ca.cmpt362.projects.WeCareApp.FetchData
import ca.cmpt362.projects.weCareApp.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FindAClinicActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private var fusedLocationProviderClient //all the connection
            : FusedLocationProviderClient? = null
    private val Request_code = 101
    private var lat = 0.0
    private  var lng:kotlin.Double = 0.0
    private lateinit var hospital: ImageButton
    private val google_maps_key = "AIzaSyDlvsNx6nCQDEx_4S9Mw1uacefM8AfP1M8"


    fun onCreateView(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        hospital = findViewById(R.id.hospital)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this.applicationContext)


        hospital.setOnClickListener(View.OnClickListener {
            val stringBuilder =
                StringBuilder("http://maps.googleapis.com/maps/api/place/nearbysearch/json?")
            stringBuilder.append("location=$lat, $lng")
            stringBuilder.append("&radius=1000")
            stringBuilder.append("&type=clinic")
            stringBuilder.append("&sensor=true")
            stringBuilder.append("&key=" + google_maps_key)
            val url = stringBuilder.toString()
            val dataFetch = arrayOfNulls<Any>(2)
            dataFetch[0] = mMap
            dataFetch[1] = url
            val fetchData = FetchData()
            fetchData.execute(dataFetch)
        })
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()
    }
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission( //check location permission
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Request_code
            ) //permission not granted, request permission
            return
        }
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 60000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.fastestInterval = 5000
        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Toast.makeText(
                    applicationContext,
                    "location result is: $locationResult",
                    Toast.LENGTH_LONG
                ).show()
                if (locationResult == null) {
                    Toast.makeText(
                        applicationContext,
                        "Current location is null",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        Toast.makeText(
                            applicationContext,
                            "location result is: " + location.longitude,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                lat = location.latitude
                lng = location.longitude
                val latLng = LatLng(lat, lng)
                mMap.addMarker(MarkerOptions().position(latLng).title("current location"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (Request_code) {
            Request_code -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            }
        }
    }

}