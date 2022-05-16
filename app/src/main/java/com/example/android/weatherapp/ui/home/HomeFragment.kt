package com.example.android.weatherapp.ui.home

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var repository: RepositoryInterface

    private lateinit var home_weather_address: TextView
    private lateinit var home_weather_date: TextView
    private lateinit var home_weather_temp: TextView
    private lateinit var home_weather_image: ImageView
    private lateinit var home_weather_status: TextView

    private lateinit var hourlyRecyclerView: RecyclerView
    private lateinit var dailyRecyclerView: RecyclerView

    private lateinit var home_pressure_status: TextView
    private lateinit var home_humidity_status: TextView
    private lateinit var home_wind_status: TextView
    private lateinit var home_cloud_status: TextView
    private lateinit var home_ultraViolet_status: TextView
    lateinit var home_visibility_status: TextView

    lateinit var dailyAdapter: HomeDailyAdapter
    lateinit var hourlyAdapter: HomeHourlyAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI(view)
        initRecyclerViews()

        sharedPref = SharedPreferencesProvider(this.context)
        repository = Repository.getInstance(this.activity?.application, sharedPref = sharedPref)



        if (!isConnected()){
            Log.i("NO_INTERNET", "No Internet")
        }
        else{
            viewModel = ViewModelProvider(this, HomeViewModelFactory(repository, sharedPref))[HomeViewModel::class.java]
            viewModel.currentWeatherLiveData?.observe(viewLifecycleOwner) {
                Log.i("RETURNED_WEATHER_DATA", "LAT" + it.lat.toString() + "LON" + it.lon.toString())
                Log.i("RETURNED_WEATHER_DATA", "anything")
                setDataToUI(it)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private fun isConnected(): Boolean {
        val connectivityManager = this.activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                sharedPref.setIsTheInternetEnabled(true)
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                sharedPref.setIsTheInternetEnabled(true)
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                sharedPref.setIsTheInternetEnabled(true)
                return true
            }
        }
        sharedPref.setIsTheInternetEnabled(false)
        return false
    }

    private fun initializeUI(view: View){
        home_weather_address = view.findViewById(R.id.home_weather_address)
        home_weather_temp = view.findViewById(R.id.home_weather_temp)
        home_weather_image = view.findViewById(R.id.home_weather_image)
        home_weather_date = view.findViewById(R.id.home_weather_date)
        home_weather_status = view.findViewById(R.id.home_weather_status)


        home_pressure_status = view.findViewById(R.id.home_pressure_status)
        home_humidity_status = view.findViewById(R.id.home_humidity_status)
        home_wind_status = view.findViewById(R.id.home_wind_status)
        home_cloud_status = view.findViewById(R.id.home_cloud_status)
        home_ultraViolet_status = view.findViewById(R.id.home_ultraViolet_status)
        home_visibility_status = view.findViewById(R.id.home_visibility_status)

        hourlyRecyclerView = view.findViewById(R.id.home_daily_recyclerView)
        dailyRecyclerView = view.findViewById(R.id.home_weekly_recyclerView)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataToUI(currentWeather: CurrentWeather){
        home_weather_address.text = currentWeather.timezone

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
        val longDate = currentWeather.current.dt
        val formatted = formatter.format(longDate.times(1000L))
        home_weather_date.text = formatted

        home_weather_temp.text = currentWeather.current.temp.toString()
        home_weather_status.text = currentWeather.current.weather[0].description

        val urlIcon = "http://openweathermap.org/img/wn/${currentWeather.current.weather[0].icon}@2x.png"
        this.context?.let { Glide.with(it).asBitmap().load(urlIcon).into(home_weather_image) }

        home_wind_status.text =  "${currentWeather.current.windSpeed} metre/sec"
        home_humidity_status.text = "${currentWeather.current.humidity} %"
        home_pressure_status.text = "${currentWeather.current.pressure} hpa"
        home_cloud_status.text = "${currentWeather.current.clouds} %"
        home_visibility_status.text = "${currentWeather.current.visibility} metres"
        home_ultraViolet_status.text = "${currentWeather.current.feelsLike} kelvin"

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
        dailyLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        dailyAdapter = HomeDailyAdapter(this.requireContext())

        hourlyRecyclerView.layoutManager = dailyLayoutManager
        hourlyRecyclerView.adapter = dailyAdapter


    }
}