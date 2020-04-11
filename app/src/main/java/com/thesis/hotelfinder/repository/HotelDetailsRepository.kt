package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator
import com.thesis.hotelfinder.api.response.HotelDetailsResponse
import com.thesis.hotelfinder.db.AppDatabase
import com.thesis.hotelfinder.db.dao.HotelDetailsDao
import com.thesis.hotelfinder.model.HotelDetails
import com.thesis.hotelfinder.util.AppExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelDetailsRepository(context: Context,
                             private var hotelDetailsDao: HotelDetailsDao =
                                 AppDatabase.getDatabase(context)!!.hotelDetailsDao()){

    companion object {
        private var instance : HotelDetailsRepository? = null
        fun getInstance(context : Context) : HotelDetailsRepository {
            if (instance == null) {
                instance = HotelDetailsRepository(context)
            }

            return instance as HotelDetailsRepository
        }
    }

    fun getHotelDetailsListFromLocationId(location_id: Int): LiveData<Resource<HotelDetails>> {

        return object : NetworkBoundResource<HotelDetails, HotelDetailsResponse>(AppExecutors.getInstance()) {

            override fun saveCallResult(item: HotelDetailsResponse) {
                Log.i("REPO", "Inserting value into DB")
              //  Log.i("isSucc", GsonBuilder().setPrettyPrinting().create().toJson(item.data))
                CoroutineScope(Dispatchers.IO).launch {
                    hotelDetailsDao.insertHotelDetails(item.data[0])
                }

            }

            override fun shouldFetch(data: HotelDetails?): Boolean{
                return data == null
            }

            override fun loadFromDb(): LiveData<HotelDetails> {
                Log.i("REPO", "Loading from DB")
                return hotelDetailsDao.getHotelDetailsByLocationId(location_id)
            }

            override fun createCall(): LiveData<Resource<HotelDetailsResponse>> {
                return ServiceGenerator.tripAdvisorApiService.getHotelDetailsListFromLocationId(location_id)
            }

        }.asLiveData()
    }
}