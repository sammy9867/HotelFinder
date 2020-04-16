package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.viewmodel.LocationSearchViewModel
import com.google.gson.GsonBuilder
import com.thesis.hotelfinder.adapter.OnCountryListener
import com.thesis.hotelfinder.adapter.StaggeredRecyclerAdapter
import com.thesis.hotelfinder.databinding.FragmentLocationSearchBinding
import com.thesis.hotelfinder.model.Country
import com.thesis.hotelfinder.model.LocationSearch
import com.thesis.hotelfinder.util.CountryData
import com.thesis.hotelfinder.viewmodel.MyViewModelFactory


class LocationSearchFragment : Fragment(), OnCountryListener {

    private lateinit var locationSearchViewModel: LocationSearchViewModel
    private var countryList : MutableList<Country> = mutableListOf()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding : FragmentLocationSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_location_search, container, false
        )

        locationSearchViewModel =  ViewModelProviders.of(this, MyViewModelFactory(context!!)).
            get(LocationSearchViewModel::class.java)

        // Search destination
        binding.locationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                if(query == null || query.isEmpty()){
                    Toast.makeText(context, "Query is empty", Toast.LENGTH_SHORT).show()
                }else{
                    Log.i("Name:", query.toLowerCase())
                    searchHotels(query.toLowerCase())
                }

                return false
            }

        })

        // Recycler view for unSplash images
        getPhotos(binding)

        return binding.root
    }

    private fun searchHotels(query: String){
        locationSearchViewModel.getLocationIdFromLocationSearch(query, "GBP").
            observe(this, Observer<Resource<LocationSearch>>{ locationSearchResponse ->

                when{
                    locationSearchResponse.status.isLoading() ->{
                        Toast.makeText(context,"isLoading", Toast.LENGTH_SHORT).show()
                    }
                    locationSearchResponse.status.isSuccessful() ->{
                        Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                        Log.i("isSucc", GsonBuilder().setPrettyPrinting().create().toJson(locationSearchResponse.data))
                        if(locationSearchResponse.data != null){
                            val bundle = bundleOf("location_id" to locationSearchResponse.data!!.location_id)
                            view!!.findNavController().navigate(R.id.action_locationSearchFragment_to_hotelFragment, bundle)
                        }


                    }

                    locationSearchResponse.status.isError() ->{
                        Toast.makeText(context, "isError", Toast.LENGTH_SHORT).show()
                        Log.i("error", GsonBuilder().setPrettyPrinting().create().toJson(locationSearchResponse.errorMessage))
                    }
                }

            })


    }

    private fun getPhotos(binding : FragmentLocationSearchBinding){
        countryList = CountryData().getCountryList()
        Log.i("CountryList SIZE", countryList.size.toString())

        val adapter = StaggeredRecyclerAdapter(context!!, countryList, this)
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvPhotos.layoutManager =  manager
        binding.rvPhotos.setHasFixedSize(true)
        binding.rvPhotos.adapter = adapter

    }

    override fun onCountryClick(position: Int) {
        val country = countryList[position]
        Log.i("Name:", country.name)
        searchHotels(country.name)
    }
}
