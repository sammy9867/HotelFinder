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
import com.thesis.hotelfinder.api.response.HotelsResponse
import com.thesis.hotelfinder.databinding.FragmentHomeBinding
import com.thesis.hotelfinder.viewmodel.HotelsViewModel
import com.thesis.hotelfinder.viewmodel.HotelsViewModelFactory
import com.google.gson.GsonBuilder





class HomeFragment : Fragment() {

    private lateinit var hotelsViewModel: HotelsViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding : FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        hotelsViewModel =  ViewModelProviders.of(this, HotelsViewModelFactory(context!!)).
            get(HotelsViewModel::class.java)
        hotelsViewModel.getHotelsFromLocationSearch("Warsaw", "GBP").
            observe(this, Observer<Resource<HotelsResponse>>{hotelResponse ->

            when{
                hotelResponse.status.isLoading() ->{
                    Toast.makeText(context,"isLoading", Toast.LENGTH_SHORT).show()
                }

                hotelResponse.status.isSuccessful() ->{
                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                    Log.i("success", hotelResponse.data.toString())


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
