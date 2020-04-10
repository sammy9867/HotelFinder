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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.adapter.HotelRecyclerAdapter
import com.thesis.hotelfinder.adapter.OnHotelListener
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.databinding.FragmentHotelsBinding
import com.thesis.hotelfinder.model.Hotel
import com.thesis.hotelfinder.viewmodel.HotelsViewModel
import com.thesis.hotelfinder.viewmodel.HotelViewModelFactory


class HotelFragment : Fragment(), OnHotelListener {

    private lateinit var hotelsViewModel: HotelsViewModel
    private var hotelList: MutableList<Hotel> = mutableListOf()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        // binding object that holds all views in the given fragment
        val binding: FragmentHotelsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotels, container, false
        )

        val getLocationId = arguments?.getInt("location_id", 0)
        Log.i("Bundle", getLocationId.toString())

        hotelsViewModel =  ViewModelProviders.of(this, HotelViewModelFactory(context!!)).
            get(HotelsViewModel::class.java)
        hotelsViewModel.getHotelsListFromLocationId(getLocationId!!, "2020-04-20", 1, 1).
            observe(this, Observer<Resource<List<Hotel>>>{ hotelResponse ->

                when{
                    hotelResponse.status.isLoading() ->{
                        binding.progressBarHotels.show()
                    }

                    hotelResponse.status.isSuccessful() -> {
                        binding.progressBarHotels.hide()
                        val adapter = HotelRecyclerAdapter(context!!, hotelList, this)
                        binding.rvHotel.adapter = adapter
                        binding.rvHotel.layoutManager = LinearLayoutManager(context)

                        if(hotelResponse.data != null){
                            for (i in hotelResponse.data!!) {
                                if(i.photo != null){
                                    hotelList.add(
                                        Hotel(i.location_id, i.location_search_id, i.name, i.latitude, i.longitude, i.num_reviews?:0,
                                            i.ranking?: "", i.rating?:0f, i.price_level?: "", i.price?: "", i.photo)
                                    )
                                }

                            }

                            binding.rvHotel.adapter!!.notifyDataSetChanged()
                        }else{
                            Toast.makeText(context, "HotelResponse is null", Toast.LENGTH_SHORT).show()
                        }




                    }



                    hotelResponse.status.isError() ->{
                        binding.progressBarHotels.hide()
                        Toast.makeText(context, "isError", Toast.LENGTH_SHORT).show()
                        Log.i("error", GsonBuilder().setPrettyPrinting().create().toJson(hotelResponse.errorMessage))
                    }
                }

            })


        return binding.root
    }

    override fun onHotelClick(position: Int) {
        val hotel = hotelList[position]
        val bundle = bundleOf("hotel_location_id" to  hotel.location_id)
        view!!.findNavController().navigate(R.id.action_hotelSearchFragment_to_hotelDetailsFragment, bundle)
    }
}
