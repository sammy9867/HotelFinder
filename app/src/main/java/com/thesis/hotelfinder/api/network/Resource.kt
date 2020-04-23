package com.thesis.hotelfinder.api.network

/**
 * A generic class holding the call status
 */
data class Resource<out T>(val status: Status, val data: T?, val errorMessage: String?){
    companion object{
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null)
        fun <T> error(message: String, data: T?): Resource<T> = Resource(Status.ERROR, data, message)
    }
}

