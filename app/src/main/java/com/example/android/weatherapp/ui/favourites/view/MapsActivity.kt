package com.example.android.weatherapp.ui.favourites.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.android.weatherapp.R
import com.example.android.weatherapp.databinding.ActivityMapsBinding
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.data.Favourite
import com.example.android.weatherapp.model.repository.Repository
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.favourites.view_model.FavouritesViewModel
import com.example.android.weatherapp.ui.favourites.view_model.FavouritesViewModelFactory
import java.io.IOException
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var repository: Repository
    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var favourite: Favourite
    lateinit var favouritesViewModel: FavouritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        sharedPref = SharedPreferencesProvider(this)
        repository = Repository.getInstance(this.application, sharedPref = sharedPref)
        favouritesViewModel = ViewModelProvider(this, FavouritesViewModelFactory(repository)).get(FavouritesViewModel::class.java)

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
        var markerOptions : MarkerOptions = MarkerOptions().position(LatLng(26.8206,  30.8025)).title(getAddressFromLatLng(26.8206,  30.8025))

        mMap.addMarker(markerOptions)
        mMap.setOnMapClickListener {
            var markerOptions : MarkerOptions = MarkerOptions().position(it).title(getAddressFromLatLng(it.latitude, it.longitude))
            mMap.clear()
            mMap.addMarker(markerOptions)
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
            favourite = Favourite(it.latitude, it.longitude, getAddressFromLatLng(it.latitude,it.longitude))
            onSaveToFavouriteBtn(favourite)
        }
    }

    fun getAddressFromLatLng(lat:Double,longg:Double) : String {
        var geocoder = Geocoder(this , Locale.getDefault())

        var addresses: List<Address> = geocoder.getFromLocation(lat, longg, 1)

        if (addresses.size > 0) {
            return addresses.get(0).getAddressLine(0)
        }
        return ""
    }

        private fun onSaveToFavouriteBtn(favourite: Favourite) {
        var confirmDialog = AlertDialog.Builder(this)
        confirmDialog.setMessage("Are you sure You wanna save to favourites?")
            .setCancelable(true)
            .setPositiveButton("Save"){
                    dialogInterface, i ->
                favouritesViewModel.insertFavourite(favourite)
                var sharedPref = SharedPreferencesProvider(this)
                sharedPref.setLatLong(favourite.lat.toString(), favourite.lon.toString())
                finish()
            }
            .setNegativeButton("Cancel"){
                    dialogInterface, i ->  dialogInterface.cancel()
            }
        val alert = confirmDialog.create()
        alert.setTitle("Confirm Save To Favourite")
        alert.show()
    }
}