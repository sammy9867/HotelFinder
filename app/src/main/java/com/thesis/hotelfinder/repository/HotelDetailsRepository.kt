package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

            override fun shouldFetch(data: HotelDetails?): Boolean = data == null

            override fun loadFromDb(): LiveData<HotelDetails> {
                Log.i("REPO", "Loading from DB")
                val hotelDetailsLiveData: MutableLiveData<HotelDetails> = MutableLiveData()
                CoroutineScope(Dispatchers.IO).launch{
                    val hotelDetails =  hotelDetailsDao.getHotelDetailsByLocationId(location_id)
                    if(hotelDetails != null){
                        Log.i("REPO", "HotelDetails is not null")
                        hotelDetailsLiveData.postValue(hotelDetails)
                    }else{
                        Log.i("REPO", "HotelDetails is null")
                        hotelDetailsLiveData.postValue(null)
                    }
                }

                return hotelDetailsLiveData

            }

            override fun createCall(): LiveData<Resource<HotelDetailsResponse>> {
                return ServiceGenerator.tripAdvisorApiService.getHotelDetailsListFromLocationId(location_id)
            }


        }.asLiveData()
    }
}