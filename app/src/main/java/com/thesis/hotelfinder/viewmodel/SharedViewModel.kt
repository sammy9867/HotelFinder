package com.thesis.hotelfinder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var locationId = MutableLiveData<Int>()

    var checkIn = MutableLiveData<String>()
    var adults = MutableLiveData<Int>()
    var rooms = MutableLiveData<Int>()
    var nights = MutableLiveData<Int>()
    var maxPrice = MutableLiveData<Int>()
    var hotelClass = MutableLiveData<Float>()
    var amenities = MutableLiveData<String>()

    fun setLocationSearchId(locationId: Int){
        this.locationId.value = locationId
    }

    fun setCheckIn(checkIn: String){
        this.checkIn.value = checkIn
    }

    fun setAdults(adults: Int){
        this.adults.value = adults
    }

    fun setRooms(rooms: Int){
        this.rooms.value= rooms
    }

    fun setNights(nights: Int){
        this.nights.value = nights
    }

    fun setMaxPrice(maxPrice: Int){
        this.maxPrice.value = maxPrice
    }

    fun setHotelClass(hotelClass: Float){
        this.hotelClass.value = hotelClass
    }

    fun setAmenities(amenities: String){
        this.amenities.value = amenities
    }

}