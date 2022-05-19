package com.example.android.weatherapp.ui.favourites.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.weatherapp.R
import com.example.android.weatherapp.model.data.Favourite
import com.example.android.weatherapp.model.repository.Repository
import com.example.android.weatherapp.services.SharedPreferencesProvider
import com.example.android.weatherapp.ui.favourite_details.view.FavoriteDetailsActivity
import com.example.android.weatherapp.ui.favourites.view_model.FavouritesViewModel
import com.example.android.weatherapp.ui.favourites.view_model.FavouritesViewModelFactory
import com.example.android.weatherapp.ui.home.view_model.HomeViewModel
import com.example.android.weatherapp.ui.home.view_model.HomeViewModelFactory
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment(), OnClickListener {

    private lateinit var repository: Repository
    private lateinit var sharedPref: SharedPreferencesProvider
    private lateinit var progressDialog: ProgressDialog
    lateinit var favouriteRecyclerView: RecyclerView
    lateinit var favouritesAdapter: FavouritesAdapter
    lateinit var viewModel: FavouritesViewModel
    lateinit var addFavouriteBtn: FloatingActionButton
    lateinit var NoFavouriteTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFavouriteBtn = view.findViewById(R.id.add_favourite_btn)
        Log.i("Favourite_Fragment", "addFavouriteBtn")
        favouriteRecyclerView = view.findViewById(R.id.favourites_recycler_view)
        Log.i("Favourite_Fragment", "favouriteRecyclerView")
        showProgressDialog()
        Log.i("Favourite_Fragment", "showProgressDialog")
        initRecyclerView()
        Log.i("Favourite_Fragment", "initRecyclerView")
        sharedPref = SharedPreferencesProvider(this.context)
        sharedPref.setIsFavourite(true)
        repository = Repository.getInstance(this.activity?.application, sharedPref = sharedPref)
        viewModel = ViewModelProvider(this, FavouritesViewModelFactory(repository)).get(FavouritesViewModel::class.java)
        getFavourites()

        Log.i("Favourite_Fragment", "getFavourites")
        if (checkConnectivityService()){
            onClickAddFavouriteBtn()
        }

        Log.i("Favourite_Fragment", "checkConnectivityService")
    }

    private fun getFavourites() {
        //lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllFavourites().observe(viewLifecycleOwner){
                if(it.isNotEmpty()){
                    favouritesAdapter.setFavouriteList(it)
                    progressDialog.dismiss()
                }
                else{
                    progressDialog.dismiss()
                }
            }
        //}

    }

    private fun showProgressDialog() {
        progressDialog = ProgressDialog(this.requireContext())
        progressDialog.setCancelable(false) // set cancelable to false
        progressDialog.setMessage("Please Wait") // set message
        progressDialog.show()
    }

    private fun initRecyclerView(){
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this.requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        favouritesAdapter = FavouritesAdapter(this)

        favouriteRecyclerView.adapter = favouritesAdapter
        favouriteRecyclerView.layoutManager = linearLayoutManager

    }

    private fun onClickAddFavouriteBtn(){
        addFavouriteBtn.setOnClickListener{
            var intent = Intent(requireContext(), MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkConnectivityService() : Boolean{
        var available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireActivity())
        when{
            available == ConnectionResult.SUCCESS -> return true
            GoogleApiAvailability.getInstance().isUserResolvableError(available) -> {
                var dialog : Dialog? = GoogleApiAvailability.getInstance().getErrorDialog(this, available, 22)
                dialog?.show()
            }
            else -> Toast.makeText(requireContext(), "Sorry You can't go to Map", Toast.LENGTH_SHORT).show()
        }
        return false
    }

    override fun onDeleteBtnClicked(favourite: Favourite) {
        var confirmDialog = AlertDialog.Builder(this.requireContext())
        confirmDialog.setMessage("Are you sure You wanna delete this favourite?")
            .setCancelable(true)
            .setPositiveButton("Ok"){
                    dialogInterface, i ->
                viewModel.deleteFavourite(favourite)
                favouritesAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel"){
                    dialogInterface, i ->  dialogInterface.cancel()
            }
        val alert = confirmDialog.create()
        alert.setTitle("Delete Favourite")
        alert.show()
    }


    override fun onFavouriteClicked(favourite: Favourite) {
        if(isConnectedToInternet()){
            var sharedPref = SharedPreferencesProvider(this.requireContext())
            sharedPref.setLatLongFav(favourite.lat.toString(), favourite.lon.toString())
            sharedPref.setIsFavourite(true)
            var intent : Intent = Intent(activity, FavoriteDetailsActivity::class.java)
            intent.putExtra("fromFavToDetails", favourite)
            startActivity(intent)
        }
        else{
            Toast.makeText(this.requireContext(), "Please connect To the Internet First", Toast.LENGTH_SHORT).show()
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

}