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
import com.thesis.hotelfinder.databinding.FragmentHotelsBinding
import com.thesis.hotelfinder.model.Hotel
import com.thesis.hotelfinder.model.HotelFilter
import com.thesis.hotelfinder.viewmodel.HotelsViewModel
import com.thesis.hotelfinder.viewmodel.MyViewModelFactory


class HotelFragment : Fragment(), OnHotelListener {

    private lateinit var binding: FragmentHotelsBinding
    private lateinit var hotelsViewModel: HotelsViewModel
    private var hotelList: MutableList<Hotel> = mutableListOf()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        // binding object that holds all views in the given fragment
        val dataBinding: FragmentHotelsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotels, container, false
        )
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        val getLocationId = arguments?.getInt("location_id", 0)
        var hotelFilter = arguments?.getParcelable<HotelFilter>("hotel_filter")
        if(hotelFilter == null){
            //Log.i("hotel Filter", "NULL")
            hotelFilter = HotelFilter(30, 3.0f)
        }

        Log.i("Bundle", getLocationId.toString())

        hotelsViewModel =  ViewModelProviders.of(this, MyViewModelFactory(context!!)).
            get(HotelsViewModel::class.java)

        // List hotels
        listHotels(binding, getLocationId!!, hotelFilter)

        // back button
        navigateBack(binding)

        // filter button
        filterButton(binding, getLocationId)

    }

    private fun navigateBack(binding: FragmentHotelsBinding){
        binding.hotelBackIb.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelFragment_to_locationSearchFragment)
        }
    }

    private fun filterButton(binding: FragmentHotelsBinding, getLocationId: Int){
        binding.hotelFilterIb.setOnClickListener{
            val bundle = bundleOf("location_id" to  getLocationId)
            view!!.findNavController().navigate(R.id.action_hotelFragment_to_hotelFilterFragment, bundle)
        }
    }

    private fun listHotels(binding: FragmentHotelsBinding, getLocationId: Int, hotelFilter: HotelFilter){
      //  Log.i("hotel Filter HAHA", "" +hotelFilter.max_price + " " + hotelFilter.hotel_class)

        hotelsViewModel.getHotelsListFromLocationId(getLocationId, "2020-05-20", 1, 1, 1,
        hotelFilter.max_price, hotelFilter.hotel_class, "free_internet").
            observe(this, Observer<Resource<List<Hotel>>>{ hotelResponse ->

                if(hotelResponse?.data != null){
                    binding.progressBarHotels.hide()

                    val adapter = HotelRecyclerAdapter(context!!, hotelList, this)
                    binding.rvHotel.adapter = adapter
                    binding.rvHotel.layoutManager = LinearLayoutManager(context)

                    for(i in hotelResponse.data){
                        if(i.photo != null){
                            hotelList.add(
                                Hotel(i.location_id, i.location_search_id, i.max_price, i.hotel_class, i.name, i.latitude, i.longitude, i.num_reviews?:0,
                                    i.ranking?: "", i.rating?:0f, i.price_level?: "", i.price?: "", i.photo)
                            )
                        }
                    }

                    binding.rvHotel.adapter!!.notifyDataSetChanged()
                }else{
                    binding.progressBarHotels.show()
                    Log.i("Fragment error", GsonBuilder().setPrettyPrinting().create().toJson(hotelResponse.errorMessage))
                }
            })
    }

    override fun onHotelClick(position: Int) {
        val hotel = hotelList[position]
        val bundle = bundleOf("hotel_location_id" to  hotel.location_id)
        view!!.findNavController().navigate(R.id.action_hotelFragment_to_hotelDetailsFragment, bundle)
    }
}
