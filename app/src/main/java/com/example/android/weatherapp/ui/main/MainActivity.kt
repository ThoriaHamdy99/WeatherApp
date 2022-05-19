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
import com.example.android.weatherapp.ui.favourites.view.FavouritesFragment
import com.example.android.weatherapp.ui.home.view.HomeFragment
import com.example.android.weatherapp.ui.settings.SettingsFragment
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var navigationMenu: NavigationView

    override fun onStart() {
        super.onStart()
    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
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
        //fragmentManager.beginTransaction()
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
}