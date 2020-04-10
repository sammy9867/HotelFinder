package com.thesis.hotelfinder.db.dao

import androidx.room.*
import com.thesis.hotelfinder.model.HotelDetails

@Dao
interface HotelDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotelDetails(hotelDetails: HotelDetails)

    @Query("select * from hotel_details_table where location_id = :locationSearchId")
    fun getHotelDetailsByLocationId(locationSearchId: Int) : HotelDetails?
}