package com.thesis.hotelfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thesis.hotelfinder.model.HotelDetails

@Dao
interface HotelDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotelDetails(hotelDetails: HotelDetails)

    @Query("SELECT * FROM hotel_details_table WHERE location_id = :locationSearchId")
    fun getHotelDetailsByLocationId(locationSearchId: Int) : LiveData<HotelDetails>
}