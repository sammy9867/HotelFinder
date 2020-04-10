package com.thesis.hotelfinder.api.network

/**
 * A generic class holding the call status
 */
data class Resource<T>(
    var status: Status,
    var data: T? = null,
    var errorMessage: String? = null
){
    companion object{
        fun <T> success(data: T): Resource<T> = Resource(Status.SUCCESS, data)
        fun <T> loading(): Resource<T> = Resource(Status.LOADING)
        fun <T> error(message: String?): Resource<T> = Resource(Status.ERROR, errorMessage = message)
    }
}

