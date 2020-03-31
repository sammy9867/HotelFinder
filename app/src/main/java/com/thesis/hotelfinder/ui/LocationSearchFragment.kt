package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

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
            observe(this, Observer<Resource<LocationSearchResponse>>{ locationSearchResponse ->

            when{
                locationSearchResponse.status.isLoading() ->{
                    Toast.makeText(context,"isLoading", Toast.LENGTH_SHORT).show()
                }
                locationSearchResponse.status.isSuccessful() ->{
                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                    var bundle = Bundle()
                    for(i in locationSearchResponse.data!!.data){
                        if(i.result_type == "geos"){
                            val locationSearch = i.result_object
                            Log.i("success" , locationSearch.name + " " + locationSearch.location_id.toString())
                            bundle = bundleOf("location_id" to locationSearch.location_id)
                        }
                    }

                    binding.locationSearch.setOnClickListener {
                        view!!.findNavController().navigate(R.id.action_locationSearchFragment_to_hotelSearchFragment, bundle)
                    }



                }

                locationSearchResponse.status.isError() ->{
                    Toast.makeText(context, "isError", Toast.LENGTH_SHORT).show()
                    Log.i("error", GsonBuilder().setPrettyPrinting().create().toJson(locationSearchResponse.errorMessage))
                }
            }

        })








        return binding.root
    }

}
