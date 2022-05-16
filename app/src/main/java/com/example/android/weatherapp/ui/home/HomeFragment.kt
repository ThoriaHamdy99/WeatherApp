package com.example.android.weatherapp.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.android.weatherapp.R
import com.example.android.weatherapp.model.local_source.LocalDataSource
import com.example.android.weatherapp.model.remote_source.RemoteDataSource
import com.example.android.weatherapp.model.remote_source.RemoteDataSourceInterface
import com.example.android.weatherapp.model.repository.Repository
import com.example.android.weatherapp.model.repository.RepositoryInterface
import com.example.android.weatherapp.services.SharedPreferencesProvider

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    lateinit var sharedPref: SharedPreferencesProvider
    lateinit var repository: RepositoryInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPreferencesProvider(this.context)
        repository = Repository.getInstance(this.activity?.application, sharedPref = sharedPref)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository))[HomeViewModel::class.java]

        viewModel.currentWeatherLiveData?.observe(viewLifecycleOwner) {
            Log.i("RETURNED_WEATHER_DATA", "LAT" + it.lat.toString() + "LON" + it.lon.toString())
            Log.i("RETURNED_WEATHER_DATA", "anything")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}