package com.thesis.hotelfinder.ui


import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.lifecycle.Observer

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelFilterBinding
import com.thesis.hotelfinder.model.HotelFilter
import com.thesis.hotelfinder.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_hotel_filter.*
import java.text.SimpleDateFormat
import java.util.*


class HotelFilterFragment : Fragment() {

    private lateinit var binding: FragmentHotelFilterBinding
    private lateinit var sharedViewModel: SharedViewModel
    lateinit var hotelFilter: HotelFilter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding object that holds all views in the given fragment
        val dataBinding: FragmentHotelFilterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_filter, container, false
        )
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.lifecycleOwner = viewLifecycleOwner
        sharedViewModel =  ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

        // Apply Default Filter
        defaultFilter()

        setDateFromDialog()
        setAdults()
        setRooms()
        setNights()
        setRange()

        // Apply Filters
        applyFilters()

        // Reset Filters
        resetFilters()

        setObservers()

        // Navigate back to HotelFragment
        filterNavigateBack()

        // Navigate to HotelAmenityFragment
        filterNavigateToAmenities()

    }

    private fun applyFilters(){
        binding.filterApplyBtn.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment)
        }
    }

    private fun resetFilters(){
        binding.filterResetBtn.setOnClickListener{
            sharedViewModel.checkIn.value = null
            sharedViewModel.adults.value = null
            sharedViewModel.rooms.value = null
            sharedViewModel.nights.value = null
            sharedViewModel.maxPrice.value = null
            sharedViewModel.hotelClass.value = null
            sharedViewModel.amenities.value = null

            Log.i("onClick", "Reset")
            defaultFilter()
        }
    }

    private fun filterNavigateBack(){
        binding.hotelFilterBackIb.setOnClickListener{
            // default filter if for the first time
            // But get the previously applied value if applied is not pressed
            view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment)
        }
    }

    private fun filterNavigateToAmenities(){
        binding.filterAmenitiesBtn.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelAmenityFragment)
        }
    }

    private fun setObservers(){

        sharedViewModel.checkIn.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    binding.checkInTv.text = o as String
                }
            }
        })

        sharedViewModel.adults.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    val adults = o as Int
                    binding.adultsTv.text = adults.toString()
                }
            }
        })

        sharedViewModel.rooms.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    val rooms = o as Int
                    binding.roomsTv.text = rooms.toString()
                }
            }
        })

        sharedViewModel.nights.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    val nights = o as Int
                    binding.nightsTv.text = nights.toString()
                }
            }
        })

        sharedViewModel.maxPrice.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    val maxPrice = o as Int
                    binding.filterPriceRange.progress = maxPrice
                }
            }
        })

        sharedViewModel.hotelClass.observe(this, object : Observer<Any>{
            override fun onChanged(o: Any?) {
                if(o != null){
                    val hotelClass = o as Float
                    binding.filterHotelClass.rating = hotelClass
                }
            }
        })
    }

    private fun defaultFilter(){
        hotelFilter  = HotelFilter(30, 3.0f)

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

    }

    private fun setDateFromDialog(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.checkInTv.setOnClickListener{
            val datePickerDialog = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->

                val incMonth = mMonth + 1
                val dayFormatted = String.format("%02d", mDay)
                val monthFormatted = String.format("%02d", incMonth)
                val dateString = "" + mYear + "-" + monthFormatted + "-" + dayFormatted
                Log.i("HotelFilter", dateString)
                sharedViewModel.setCheckIn(dateString)
            }, year, month, day)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1
            datePickerDialog.show()
        }

    }

    private fun setAdults(){
        binding.adultsDec.setOnClickListener{
            val adultTextView = adultsTv.text.toString()
            var adultsInt = adultTextView.toInt()
            if(adultsInt <= 1){
                adultsInt = 1
            }else{
                adultsInt--
            }
            Log.i("HotelFilter", adultsInt.toString())
            sharedViewModel.adults.value = adultsInt
        }

        binding.adultsInc.setOnClickListener{
            val adultTextView = adultsTv.text.toString()
            var adultsInt = adultTextView.toInt()
            if(adultsInt >= 5){
                adultsInt = 5
            }else{
                adultsInt++
            }
            Log.i("HotelFilter", adultsInt.toString())
            sharedViewModel.adults.value = adultsInt
        }
    }

    private fun setRooms(){
        binding.roomsDec.setOnClickListener{
            val roomTextView = roomsTv.text.toString()
            var roomsInt = roomTextView.toInt()
            if(roomsInt <= 1){
                roomsInt = 1
            }else{
                roomsInt--
            }
            Log.i("HotelFilter", roomsInt.toString())
            sharedViewModel.rooms.value = roomsInt
        }

        binding.roomsInc.setOnClickListener{
            val roomTextView = roomsTv.text.toString()
            var roomsInt = roomTextView.toInt()
            if(roomsInt >= 5){
                roomsInt = 5
            }else{
                roomsInt++
            }
            Log.i("HotelFilter", roomsInt.toString())
            sharedViewModel.rooms.value = roomsInt
        }
    }

    private fun setNights(){
        binding.nightsDec.setOnClickListener{
            val nightTextView = nightsTv.text.toString()
            var nightsInt = nightTextView.toInt()
            if(nightsInt <= 1){
                nightsInt = 1
            }else{
                nightsInt--
            }
            Log.i("HotelFilter", nightsInt.toString())
            sharedViewModel.nights.value = nightsInt
        }

        binding.nightsInc.setOnClickListener{
            val nightTextView = nightsTv.text.toString()
            var nightsInt = nightTextView.toInt()
            if(nightsInt >= 5){
                nightsInt = 5
            }else{
                nightsInt++
            }
            Log.i("HotelFilter", nightsInt.toString())
            sharedViewModel.nights.value = nightsInt
        }
    }

    private fun setRange(){

        binding.filterPriceRange.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                sharedViewModel.setMaxPrice(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar ) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        binding.filterHotelClass.setOnRatingBarChangeListener{_, rating, _ ->
            sharedViewModel.setHotelClass(rating)
        }

    }




}
