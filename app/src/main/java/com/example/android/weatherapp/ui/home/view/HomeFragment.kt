package com.example.android.weatherapp.ui.home.view

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.weatherapp.R
import com.example.android.weatherapp.model.data.CurrentWeather
import com.example.android.weatherapp.model.repository.Repository
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.home.view_model.HomeViewModel
import com.example.android.weatherapp.ui.home.view_model.HomeViewModelFactory
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var repository: RepositoryInterface

    private lateinit var homeWeatherAddress: TextView
    private lateinit var homeWeatherDate: TextView
    private lateinit var homeWeatherTemp: TextView
    private lateinit var homeWeatherImage: ImageView
    private lateinit var homeWeatherStatus: TextView

    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView

    private lateinit var homePressureStatus: TextView
    private lateinit var homeHumidityStatus: TextView
    private lateinit var homeWindStatus: TextView
    private lateinit var homeCloudStatus: TextView
    private lateinit var homeUltraVioletStatus: TextView
    lateinit var homeVisibilityStatus: TextView

    lateinit var dailyAdapter: HomeDailyAdapter
    lateinit var hourlyAdapter: HomeHourlyAdapter

    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI(view)
        initRecyclerViews()
        showProgressDialog()

        sharedPref = SharedPreferencesProvider(this.context)
        repository = Repository.getInstance(this.activity?.application, sharedPref = sharedPref)

        var isInternetEnabled = isConnectedToInternet()
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository))[HomeViewModel::class.java]
        if (isInternetEnabled){
            viewModel.currentWeatherLiveData?.observe(viewLifecycleOwner) {
                Log.i("RETURNED_WEATHER_DATA", "Data")
                Log.i("RETURNED_WEATHER_DATA", "LAT" + it.lat.toString() + "LON" + it.lon.toString())
                progressDialog.dismiss()
                setDataToUI(it)
                viewModel.insertWeather(it)
            }
        }
        else{
            Log.i("No_Internet in else", "Data from DB")
            Log.i("No_Internet in else", "Data from DB")
            viewModel.getWeatherFromDB()?.observe(viewLifecycleOwner, {
                progressDialog.dismiss()
                Log.i("RETURNED_WEATHER_DATA", "Data from DB")
                Log.i("RETURNED_WEATHER_DATA", "LAT" + it.lat.toString() + "LON" + it.lon.toString())
                setDataToUI(it)
            })
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = this.activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun initializeUI(view: View){
        homeWeatherAddress = view.findViewById(R.id.home_weather_address)
        homeWeatherTemp = view.findViewById(R.id.home_weather_temp)
        homeWeatherImage = view.findViewById(R.id.home_weather_image)
        homeWeatherDate = view.findViewById(R.id.home_weather_date)
        homeWeatherStatus = view.findViewById(R.id.home_weather_status)


        homePressureStatus = view.findViewById(R.id.home_pressure_status)
        homeHumidityStatus = view.findViewById(R.id.home_humidity_status)
        homeWindStatus = view.findViewById(R.id.home_wind_status)
        homeCloudStatus = view.findViewById(R.id.home_cloud_status)
        homeUltraVioletStatus = view.findViewById(R.id.home_ultraViolet_status)
        homeVisibilityStatus = view.findViewById(R.id.home_visibility_status)

        hourlyRecyclerView = view.findViewById(R.id.home_hourly_recyclerView)
        dailyRecyclerView = view.findViewById(R.id.home_daily_recyclerView)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataToUI(currentWeather: CurrentWeather){

        homeWeatherAddress.text = currentWeather.timezone

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val longDate = currentWeather.current.dt
        val formatted = formatter.format(longDate.times(1000L))
        homeWeatherDate.text = formatted

        homeWeatherTemp.text = currentWeather.current.temp.toString()
        homeWeatherStatus.text = currentWeather.current.weather[0].description

        val urlIcon = "https://openweathermap.org/img/wn/${currentWeather.current.weather[0].icon}@2x.png"

        Glide.with(this.requireContext()).asBitmap().load(urlIcon).into(homeWeatherImage)

        homeWindStatus.text =  "${currentWeather.current.windSpeed} metre/sec"
        homeHumidityStatus.text = "${currentWeather.current.humidity} %"
        homePressureStatus.text = "${currentWeather.current.pressure} hpa"
        homeCloudStatus.text = "${currentWeather.current.clouds} %"
        homeVisibilityStatus.text = "${currentWeather.current.visibility} metres"
        homeUltraVioletStatus.text = "${currentWeather.current.feelsLike} kelvin"


        dailyAdapter.setDailyList(currentWeather.daily)
        hourlyAdapter.setHourlyList(currentWeather.hourly)
    }

    private fun initRecyclerViews(){
        var hourlyLayoutManager = LinearLayoutManager(this.context)
        hourlyLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        hourlyAdapter = HomeHourlyAdapter(this.requireContext())

        hourlyRecyclerView.layoutManager = hourlyLayoutManager
        hourlyRecyclerView.adapter = hourlyAdapter

        var dailyLayoutManager = LinearLayoutManager(this.context)
        dailyLayoutManager.orientation = LinearLayoutManager.VERTICAL

        dailyAdapter = HomeDailyAdapter(this.requireContext())

        dailyRecyclerView.layoutManager = dailyLayoutManager
        dailyRecyclerView.adapter = dailyAdapter


    }
    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this.requireContext())
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Please Wait") // set message
        progressDialog.show()
    }
}