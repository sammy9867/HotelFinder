package com.thesis.hotelfinder.db.dao

import androidx.room.*
import com.thesis.hotelfinder.model.Hotel

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotels(hotels: List<Hotel>)

    @Transaction
    @Query("""select * from hotel_table where location_search_id = :locationSearchId """)
    fun getAllHotelsByLocationId(locationSearchId: Int) : List<Hotel>
}