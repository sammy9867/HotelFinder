package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.tripAdvisorApiService
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.db.AppDatabase
import com.thesis.hotelfinder.db.dao.HotelDao
import com.thesis.hotelfinder.model.Hotel
import com.thesis.hotelfinder.util.AppExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelRepository(context: Context,
                      private var hotelDao: HotelDao =
                          AppDatabase.getDatabase(context)!!.hotelDao()){

    companion object {
        private var instance : HotelRepository? = null
        fun getInstance(context : Context) : HotelRepository {
            if (instance == null) {
                instance = HotelRepository(context)
            }

            return instance as HotelRepository
        }
    }

   /* fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
                                    number_of_adults: Int, number_of_rooms :Int): LiveData<Resource<HotelResponse>> {

        return object : NetworkBoundResource<HotelResponse>() {
            override fun createCall(): LiveData<Resource<HotelResponse>> {
                return ServiceGenerator.tripAdvisorApiService.
                    getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms)
            }

        }.asLiveData()
    }*/


    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
    number_of_adults: Int, number_of_rooms :Int): LiveData<Resource<List<Hotel>>> {

        //<ResultType, RequestType>
        return object : NetworkBoundResource<List<Hotel>, HotelResponse>(AppExecutors.getInstance()) {

            override fun saveCallResult(item: HotelResponse) {
                Log.i("REPO", "Inserting value into DB")
                Log.i("REPO data", GsonBuilder().setPrettyPrinting().create().toJson(item.data))
                CoroutineScope(Dispatchers.IO).launch {

                    val hotelList: ArrayList<Hotel> = arrayListOf()
                    for(i in item.data){
                        val hotel = Hotel(i.location_id, location_id,
                            i.name, i.latitude, i.longitude, i.num_reviews, i.ranking, i.rating,
                            i.price_level, i.price, i.photo
                            )
                        hotelList.add(hotel)
                    }
                    hotelDao.insertHotels(hotelList)
                }

            }

            override fun shouldFetch(data: List<Hotel>?) = data == null

            override fun loadFromDb(): LiveData<List<Hotel>> {
                Log.i("REPO", "Loading from DB")
                val hotelListLiveData: MutableLiveData<List<Hotel>> = MutableLiveData()
                var hotelList: List<Hotel>?
                CoroutineScope(Dispatchers.IO).launch{
                    hotelList = hotelDao.getAllHotelsByLocationId(location_id)
                    if(hotelList != null && hotelList!!.isNotEmpty()){
                        Log.i("REPO SIZE", "" + hotelList!!.size)
                        Log.i("REPO", "" + hotelList!![0].location_id + " " + hotelList!![0].name)
                        hotelListLiveData.postValue(hotelList)
                    }else{
                        Log.i("REPO", "hotelList is null")
                        hotelListLiveData.postValue(null)
                    }

                }
                return hotelListLiveData
            }

            override fun createCall(): LiveData<Resource<HotelResponse>> {
                Log.i("REPO", "CALLING API")
                return tripAdvisorApiService.getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms)
            }
        }.asLiveData()
    }
}