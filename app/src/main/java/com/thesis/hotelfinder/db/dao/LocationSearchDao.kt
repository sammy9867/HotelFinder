package com.thesis.hotelfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thesis.hotelfinder.model.LocationSearch


@Dao
interface LocationSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationSearch: LocationSearch)

    @Query("SELECT * FROM location_search_table WHERE name=:name")
    fun getLocationByName(name: String) : LiveData<LocationSearch>
}