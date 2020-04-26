package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.thesis.hotelfinder.viewmodel.HotelsViewModel
import com.thesis.hotelfinder.viewmodel.MyViewModelFactory
import com.thesis.hotelfinder.viewmodel.SharedViewModel
import java.text.SimpleDateFormat
import java.util.*


class HotelFragment : Fragment(), OnHotelListener {

    private lateinit var binding: FragmentHotelsBinding
    private lateinit var hotelsViewModel: HotelsViewModel
    private lateinit var sharedViewModel: SharedViewModel
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

        hotelsViewModel =  ViewModelProviders.of(this, MyViewModelFactory(context!!)).
            get(HotelsViewModel::class.java)
        sharedViewModel =  ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)


        if(sharedViewModel.locationId.value != null){
            val locationId = sharedViewModel.locationId.value!!

            // List hotels
            listHotels(binding, locationId)
        }

        // Navigate back to LocationSearchFragment
        navigateBack()

        // Navigate to HotelFilterFragment
        navigateToFilters()

    }

    private fun listHotels(binding: FragmentHotelsBinding, getLocationId: Int){

        defaultFilter()
        val checkIn = sharedViewModel.checkIn.value
        val adults = sharedViewModel.adults.value
        val rooms = sharedViewModel.rooms.value
        val nights = sharedViewModel.nights.value
        val maxPrice = sharedViewModel.maxPrice.value
        val hotelClass =  sharedViewModel.hotelClass.value
        val amenities = sharedViewModel.amenities.value

        Log.i("HotelFilter", "" + checkIn + " " + adults + " " + rooms + " " + nights)
        Log.i("HotelFilter", "" + maxPrice + " " + hotelClass + " " + amenities)

        hotelsViewModel.getHotelsListFromLocationId(getLocationId, checkIn!!, adults!!, rooms!!, nights!!,
        maxPrice!!, hotelClass!!, amenities!!).
            observe(this, Observer<Resource<List<Hotel>>>{ hotelResponse ->

                if(hotelResponse?.data != null){
                    binding.progressBarHotels.hide()

                    hotelList.clear()
                    val adapter = HotelRecyclerAdapter(context!!, hotelList, this)
                    binding.rvHotel.adapter = adapter
                    binding.rvHotel.layoutManager = LinearLayoutManager(context)
                    if(sharedViewModel.rvScrollPostion.value != null){
                        binding.rvHotel.scrollToPosition(sharedViewModel.rvScrollPostion.value!!)
                    }

                    for(i in hotelResponse.data){
                        if(i.photo != null){
                            hotelList.add(
                                Hotel(i.location_id, i.location_search_id, i.checkIn, i.adults, i.rooms, i.nights,
                                    i.max_price, i.hotel_class, i.amenityInput,
                                    i.name, i.latitude, i.longitude, i.num_reviews?:0,
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

    private fun defaultFilter(){

        if(sharedViewModel.checkIn.value == null){
            Log.i("HotelFilter", "is Null")
            val formatterCurrDateTv  =  SimpleDateFormat("yyyy-MM-dd")
            sharedViewModel.setCheckIn(formatterCurrDateTv.format(Date()).toString())
        }

        if(sharedViewModel.adults.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setAdults(1)
        }

        if(sharedViewModel.rooms.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setRooms(1)
        }

        if(sharedViewModel.nights.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setNights(1)
        }

        if(sharedViewModel.maxPrice.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setMaxPrice(30)
        }

        if(sharedViewModel.hotelClass.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setHotelClass(3.0f)
        }

        if(sharedViewModel.amenities.value == null){
            Log.i("HotelFilter", "is Null")
            sharedViewModel.setAmenities("")
        }

    }

    override fun onHotelClick(position: Int) {
        val hotel = hotelList[position]
        val bundle = bundleOf("hotel_location_id" to  hotel.location_id)
        val layoutManager = binding.rvHotel.layoutManager
        sharedViewModel.setRvScrollPosition((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
        view!!.findNavController().navigate(R.id.action_hotelFragment_to_hotelDetailsFragment, bundle)
    }

    private fun navigateBack(){
        binding.hotelBackIb.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelFragment_to_locationSearchFragment)
        }
    }

    private fun navigateToFilters(){
        binding.hotelFilterIb.setOnClickListener{
            val layoutManager = binding.rvHotel.layoutManager
            sharedViewModel.setRvScrollPosition((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
            view!!.findNavController().navigate(R.id.action_hotelFragment_to_hotelFilterFragment)
        }
    }



}
