package com.thesis.hotelfinder.util

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.model.AmenityFilter

class AmenityFilterData{

    private val amenityFilterList: MutableList<AmenityFilter> = mutableListOf()

    private fun populateAmenityFilter(){

        amenityFilterList.add(AmenityFilter("free_internet", "Free Internet", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("free_parking", "Free Parking", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("room_service", "Room Service", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("fitness_center", "Fitness Center", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("swimming_pool", "Swimming Pool", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("pets_allowed", "Pets Allowed", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("casino", "Casino", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("restaurant", "Restaurant", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("laundry", "Laundry", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("spa", "Spa", R.drawable.ic_add, false))
        amenityFilterList.add(AmenityFilter("non_smoking_hotel", "Non-Smoking Hotel", R.drawable.ic_add, false))

    }

    fun getAmenityFilterList(): MutableList<AmenityFilter>{
        populateAmenityFilter()
        return amenityFilterList
    }

}