package com.example.android.weatherapp.ui.favourite_details.view

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android.weatherapp.databinding.ActivityFavoriteDetailsBinding
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.repository.Repository
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.home.view.HomeDailyAdapter
import com.example.android.weatherapp.ui.home.view.HomeHourlyAdapter
import com.example.android.weatherapp.ui.home.view_model.HomeViewModel
import com.example.android.weatherapp.ui.home.view_model.HomeViewModelFactory
import java.text.SimpleDateFormat

class FavoriteDetailsActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var repository: RepositoryInterface
    lateinit var dailyAdapter: HomeDailyAdapter
    lateinit var hourlyAdapter: HomeHourlyAdapter

    private lateinit var binding: ActivityFavoriteDetailsBinding
    private lateinit var coordinatorLayout: CoordinatorLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPreferencesProvider(this)
        repository = Repository.getInstance(this?.application, sharedPref = sharedPref)
        initRecyclerViews()
        showProgressDialog()
        sharedPref.setIsFavourite(true)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository))[HomeViewModel::class.java]

        viewModel.fetchWeatherData(true).observe(this){
            progressDialog.dismiss()
            binding.favouriteDetailsCoordinatorLayout.visibility = LinearLayout.VISIBLE
            setDataToUI(it)
        }
    }
    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Please Wait") // set message
        progressDialog.show()
    }

    private fun setDataToUI(currentWeather: CurrentWeather) {
        binding.homeWeatherAddress.text = currentWeather.timezone

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val longDate = currentWeather.current.dt
        val formatted = formatter.format(longDate.times(1000L))
        binding.homeWeatherDate.text = formatted

        binding.homeWeatherTemp.text = currentWeather.current.temp.toString()
        binding.homeWeatherStatus.text = currentWeather.current.weather[0].description

        val urlIcon = "https://openweathermap.org/img/wn/${currentWeather.current.weather[0].icon}@2x.png"

        Glide.with(this).asBitmap().load(urlIcon).into(binding.homeWeatherImage)

        binding.homeWindStatus.text =  "${currentWeather.current.windSpeed} metre/sec"
        binding.homeHumidityStatus.text = "${currentWeather.current.humidity} %"
        binding.homePressureStatus.text = "${currentWeather.current.pressure} hpa"
        binding.homeCloudStatus.text = "${currentWeather.current.clouds} %"
        binding.homeVisibilityStatus.text = "${currentWeather.current.visibility} metres"
        binding.homeUltraVioletStatus.text = "${currentWeather.current.feelsLike} kelvin"


        dailyAdapter.setDailyList(currentWeather.daily)
        hourlyAdapter.setHourlyList(currentWeather.hourly)
    }
    private fun initRecyclerViews(){
        var hourlyLayoutManager = LinearLayoutManager(this)
        hourlyLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        hourlyAdapter = HomeHourlyAdapter(this)

        binding.homeHourlyRecyclerView.layoutManager = hourlyLayoutManager
        binding.homeHourlyRecyclerView.adapter = hourlyAdapter

        var dailyLayoutManager = LinearLayoutManager(this)
        dailyLayoutManager.orientation = LinearLayoutManager.VERTICAL

        dailyAdapter = HomeDailyAdapter(this)

        binding.homeDailyRecyclerView.layoutManager = dailyLayoutManager
        binding.homeDailyRecyclerView.adapter = dailyAdapter
    }
}