package com.thesis.hotelfinder.api.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.thesis.hotelfinder.util.AppExecutors
import java.lang.reflect.Type


/**
 *  A generic class that can provide a resource backed by both the room database and the network.
 */
abstract class NetworkBoundResource<ResultType, RequestType>@MainThread constructor(private val appExecutors: AppExecutors){

    private val result = MediatorLiveData<Resource<ResultType>>()

    init{
        result.value = Resource.loading()
        val dbSource = this.loadFromDb()

        result.addSource(dbSource) { typeResult->

            result.removeSource(dbSource) // remove source once data is loaded

            if(shouldFetch(typeResult)){
                fetchFromNetwork(dbSource)
            }else{
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData)) }
            }
        }
    }



    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource){
           setValue(Resource.loading())
        }

        result.addSource(apiResponse){response ->

            result.removeSource(dbSource)
            result.removeSource(apiResponse)

            response?.apply {

                if(status.isSuccessful()){

                    appExecutors.diskIO().execute {
                        // Process the response on a thread
                        processResponse(this)?.let { typeRequest->
                            saveCallResult(typeRequest)
                        }

                        appExecutors.mainThread().execute {
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb()) {typeResult->
                                setValue(Resource.success(typeResult))
                            }
                        }
                    }

                }else{
                    onFetchFailed()
                    result.addSource(dbSource) {
                        result.setValue(Resource.error(errorMessage))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    protected open fun onFetchFailed(){}

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    private fun processResponse(response: Resource<RequestType>): RequestType?{
        return response.data
    }

    // To save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<Resource<RequestType>>

}