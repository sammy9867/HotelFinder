package com.thesis.hotelfinder.api.network

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


/**
 * A generic class to send loading event up-stream while fetching data
 * from network.
 */
abstract class NetworkBoundResource<TypeRequest>@MainThread constructor(){

    private val result = MediatorLiveData<Resource<TypeRequest>>()

    init{
        result.value = Resource.loading()
        fetchFromNetwork()
    }

    private fun fetchFromNetwork(){
        val apiResponse = createCall()

        result.addSource(apiResponse){response ->
                result.removeSource(apiResponse)
            response?.apply {
                when{

                    //onSuccess
                    status.isSuccessful()->{
                        setValue(this)
                    }
                    else->{
                        setValue(Resource.error(errorMessage))
                    }
                }
            }
        }
    }


    fun asLiveData(): LiveData<Resource<TypeRequest>> {
        return result
    }

    @MainThread
    private fun setValue(newValue: Resource<TypeRequest>) {
        if (result.value != newValue)
            result.value = newValue
    }


    @MainThread
    protected abstract fun createCall(): LiveData<Resource<TypeRequest>>
}