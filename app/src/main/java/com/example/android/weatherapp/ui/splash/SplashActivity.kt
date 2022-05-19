package com.example.android.weatherapp.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.android.weatherapp.R
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.main.MainActivity
import com.google.android.gms.location.*
import java.io.IOException

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferencesProvider
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var PERMISSION_ID = 1
    var addressList: List<Address>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fusedLocationClient = FusedLocationProviderClient(this)
        checkStatus()
        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, 4000)
    }

    private fun checkStatus(){
        sharedPref = SharedPreferencesProvider(context = this)
        if(sharedPref.isFirstTimeLaunch){
            Log.i("IS_FIRST_TIME", "IS FIRST TIME LAUNCH")
            checkConnectivityAndLocation()
        }
        else{
            if (isConnectedToInternet() && isLocationEnabled())
                getLocation()
        }
    }

    private fun checkConnectivityAndLocation() {
        if(!isConnectedToInternet()){
            showRequestInternetDialog()
            Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show()
        }else if(isConnectedToInternet()){
            if (!isLocationEnabled()){
                showLocationDialog("Location","Please enable Location to use Application")
            }
        }
        if(isConnectedToInternet() && isLocationEnabled()){
            sharedPref.setFirstTimeLaunch(false)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            getLocation()
        }
    }

    private fun showLocationDialog(alertTitle: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(alertTitle)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Enable Location"){
                    dialog, which ->
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                dialog.dismiss()
            }
            .setNegativeButton("Exit"){
                    dialog, which -> this.finish()
                dialog.dismiss()
            }
            .show()
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = this.application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun showRequestInternetDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please check the internet")
            .setCancelable(false)
            .setPositiveButton("Connect"){
                    dialog, which ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                dialog.dismiss()
            }
            .setNegativeButton("Exit"){
                    dialog, which ->  this.finish()
                dialog.dismiss()
            }
            .show()
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when  {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }

    @SuppressLint("MissingPermission")
    private fun getLocation(): Unit{
        if (checkPremissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation
                    .addOnCompleteListener { task ->
                        val location: Location = task.result!!
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            val geocoder = Geocoder(this)
                            try {
                                addressList = geocoder.getFromLocation(
                                    location.latitude, location.longitude, 1)

                                sharedPref.setLatLong(location.latitude.toString(),
                                    location.longitude.toString())
                            } catch (e: IOException) {
                                Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show()
                            }
                            Toast.makeText(
                                this,
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
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Granted access location", Toast.LENGTH_SHORT).show()
                getLocation()
            }
            else{
                Toast.makeText(applicationContext, "Denied access location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationReq = LocationRequest()
        locationReq.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationReq.interval = 0
        locationReq.numUpdates = 1
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Looper.myLooper()?.let {
            fusedLocationClient.requestLocationUpdates(
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
                Geocoder(applicationContext) //to change number of latitude, longitude to text address
            try {
                // max. one adress from alot ..  most acurate.
                addressList = geocoder.getFromLocation(
                    lastLocation.latitude,
                    lastLocation.longitude,
                    1
                )
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "connection failed", Toast.LENGTH_SHORT)
                    .show() // geocoder deals with internet to change from nums. to text adress so catch connections.
            }
            Toast.makeText(
                applicationContext,
                """my longitude : ${lastLocation.longitude}
                        my latitude : ${lastLocation.latitude}
                        my location is: ${addressList!![0].getAddressLine(0)}""",
                Toast.LENGTH_LONG
            ).show()
        }
    }

//    private fun checkIfLocationEnabled(): Boolean{
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }

    private fun checkPremissions(): Boolean {
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