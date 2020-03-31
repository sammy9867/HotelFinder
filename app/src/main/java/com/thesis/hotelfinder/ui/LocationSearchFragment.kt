package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.LocationSearchResponse
import com.thesis.hotelfinder.viewmodel.LocationSearchViewModel
import com.thesis.hotelfinder.viewmodel.LocationSearchViewModelFactory
import com.google.gson.GsonBuilder
import com.thesis.hotelfinder.databinding.FragmentLocationSearchBinding


class LocationSearchFragment : Fragment() {

    private lateinit var locationSearchViewModel: LocationSearchViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding : FragmentLocationSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_location_search, container, false
        )

        locationSearchViewModel =  ViewModelProviders.of(this, LocationSearchViewModelFactory(context!!)).
            get(LocationSearchViewModel::class.java)
        locationSearchViewModel.getLocationIdFromLocationSearch("London", "GBP").
            observe(this, Observer<Resource<LocationSearchResponse>>{ hotelResponse ->

            when{
                hotelResponse.status.isLoading() ->{
                    Toast.makeText(context,"isLoading", Toast.LENGTH_SHORT).show()
                }

                hotelResponse.status.isSuccessful() ->{
                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                    for(i in hotelResponse.data!!.data){
                        if(i.result_type == "geos"){
                            val locationSearch = i.result_object
                            Log.i("success", locationSearch.name + " " + locationSearch.location_id.toString())
                        }
                    }
                }

                hotelResponse.status.isError() ->{
                    Toast.makeText(context, "isError", Toast.LENGTH_SHORT).show()
                    Log.i("error", GsonBuilder().setPrettyPrinting().create().toJson(hotelResponse.errorMessage))
                }
            }

        })








        return binding.root
    }

}
