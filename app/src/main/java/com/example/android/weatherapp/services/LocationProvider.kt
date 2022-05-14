package com.example.android.weatherapp.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.android.weatherapp.R
import com.google.android.gms.location.*
import com.google.android.gms.location.FusedLocationProviderClient
import java.io.IOException

class LocationProvider : AppCompatActivity() {

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var PermissionId = 1
    var addressList: List<Address>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        lastLocation
    }// geocoder deals with internet to change from nums. to text adress so catch connections.// max. one adress from alot ..  most acurate.

    //to change number of latitude, longitude to text address
    @get:SuppressLint("MissingPermission")
    private val lastLocation: Unit
        private get() {
            if (checkPremisions()) {
                if (isLocationEnabled) {
                    mFusedLocationClient!!.lastLocation
                        .addOnCompleteListener { task ->
                            val location = task.result
                            if (location == null) {
                                requestNewLocationData()
                            } else {
                                location.longitude
                                location.latitude
                                val geocoder =
                                    Geocoder(this@LocationProvider) //to change number of latitude, longitude to text address
                                try {
                                    // max. one adress from alot ..  most acurate.
                                    addressList = geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                } catch (e: IOException) {
                                    Toast.makeText(
                                        this@LocationProvider,
                                        "connection failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show() // geocoder deals with internet to change from nums. to text adress so catch connections.
                                }
                                Toast.makeText(
                                    this@LocationProvider,
                                    """my longitude : ${location.longitude}
                                       my latitude : ${location.latitude}
                                       my location is: """ + addressList!![0]
                                        .getAddressLine(0),
                                    Toast.LENGTH_LONG
                                    ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show()
                    val `in` = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(`in`)
                }
            } else {
                requestPermisions()
            }
        }

    private fun requestPermisions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PermissionId
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionId) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lastLocation
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationReq = LocationRequest()
        locationReq.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationReq.interval = 0
        locationReq.numUpdates = 1
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            mFusedLocationClient.requestLocationUpdates(
                locationReq,
                locationCallBack,
                it
            )
        }
    }

    private val locationCallBack: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            val geocoder =
                Geocoder(this@LocationProvider) //to change number of latitude, longitude to text address
            try {
                // max. one adress from alot ..  most acurate.
                addressList = geocoder.getFromLocation(
                    lastLocation.latitude,
                    lastLocation.longitude,
                    1
                )
            } catch (e: IOException) {
                Toast.makeText(this@LocationProvider, "connection failed", Toast.LENGTH_SHORT)
                    .show() // geocoder deals with internet to change from nums. to text adress so catch connections.
            }
            Toast.makeText(
                this@LocationProvider,
                """my longitude : ${lastLocation.longitude}
                        my latitude : ${lastLocation.latitude}
                        my location is: ${addressList!![0].getAddressLine(0)}""",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private val isLocationEnabled: Boolean
        private get() {
            val locationManager =
                getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                    locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

    private fun checkPremisions(): Boolean {
        return ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }
}