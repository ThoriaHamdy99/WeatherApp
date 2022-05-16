package com.example.android.weatherapp.ui.main

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
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.android.weatherapp.R
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.alerts.AlertsFragment
import com.example.android.weatherapp.ui.favourites.FavouritesFragment
import com.example.android.weatherapp.ui.home.HomeFragment
import com.example.android.weatherapp.ui.settings.SettingsFragment
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationMenu: NavigationView

    private lateinit var sharedPref: SharedPreferencesProvider

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var PERMISSION_ID = 1
    var addressList: List<Address>? = null

    override fun onStart() {
        super.onStart()
        sharedPref = SharedPreferencesProvider(context = this)
        if(sharedPref.isFirstTimeLaunch){
            Log.i("IS_FIRST_TIME", "IS FIRST TIME LAUNCH")
            checkStatus()
        }
    }

    private fun checkStatus() {
        if(!isConnected()){
            showDialog()
            Toast.makeText(this, "Connection Failed", Toast.LENGTH_LONG).show()
        }else if(isConnected()){
            if (!checkLocation()){
                showLocationDialog("Location","Please enable Location to use Application")
            }
        }
        if(isConnected() && checkLocation()){
            sharedPref.setFirstTimeLaunch(false)
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            lastLocation
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

    private fun checkLocation(): Boolean {
        val locationManager = this.application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            sharedPref.setFirstTimeLocationenabled(true)
            return true
        }else{
            return false;
        }
    }

    private fun showDialog() {
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

    private fun isConnected(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationMenu = findViewById(R.id.navigation_menu)
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout,
            R.string.nav_open,
            R.string.nav_close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setActionBarTitle("Home")
        changeFragment(HomeFragment())

        navigationMenu.setNavigationItemSelectedListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBarTitle(title: String){
        //actionBar?.title = title
        this.title = title
    }

    private fun changeFragment(frag: Fragment){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_container, frag)
        fragment.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                setActionBarTitle("Home")
                changeFragment(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_favourites -> {
                setActionBarTitle("Favourites")
                changeFragment(FavouritesFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_Settings -> {
                setActionBarTitle("Settings")
                changeFragment(SettingsFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.nav_alerts -> {
                setActionBarTitle("Alerts")
                changeFragment(AlertsFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    //to change number of latitude, longitude to text address
    @get:SuppressLint("MissingPermission")
    private val lastLocation: Unit
        private get() {
            if (checkPremissions()) {
                if (isLocationEnabled) {
                    mFusedLocationClient!!.lastLocation
                        .addOnCompleteListener { task ->
                            val location: Location = task.result
                            if (location == null) {
                                requestNewLocationData()
                            } else {
                                location.longitude
                                location.latitude
                                val geocoder =
                                    Geocoder(this) //to change number of latitude, longitude to text address
                                try {
                                    // max. one adress from alot ..  most acurate.
                                    addressList = geocoder.getFromLocation(
                                        location.latitude,
                                        location.longitude,
                                        1
                                    )
                                    var sharedPreferencesProvider = SharedPreferencesProvider(this)
                                    sharedPreferencesProvider.setLatLong(location.latitude.toString(),
                                        location.longitude.toString())
                                    Log.i("RESPONSE_API", "lat = ${sharedPref.latLong[0]}, Lon = ${sharedPref.latLong[1]}")
                                } catch (e: IOException) {
                                    Toast.makeText(
                                        this,
                                        "connection failed",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show() // geocoder deals with internet to change from nums. to text adress so catch connections.
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
                lastLocation
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
                Geocoder(this@MainActivity) //to change number of latitude, longitude to text address
            try {
                // max. one adress from alot ..  most acurate.
                addressList = geocoder.getFromLocation(
                    lastLocation.latitude,
                    lastLocation.longitude,
                    1
                )
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "connection failed", Toast.LENGTH_SHORT)
                    .show() // geocoder deals with internet to change from nums. to text adress so catch connections.
            }
            Toast.makeText(
                this@MainActivity,
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