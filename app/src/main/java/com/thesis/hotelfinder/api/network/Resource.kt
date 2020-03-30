package com.thesis.hotelfinder.api.network

/**
 * A generic class holding the call status
 */
data class Resource<TypeResult>(
    var status: Status,
    var data: TypeResult? = null,
    var errorMessage: String? = null
){
    companion object{
        fun <TypeResult> success(data: TypeResult): Resource<TypeResult> = Resource(Status.SUCCESS, data)
        fun <TypeResult> loading(): Resource<TypeResult> = Resource(Status.LOADING)
        fun <TypeResult> error(message: String?): Resource<TypeResult> = Resource(Status.ERROR, errorMessage = message)
    }
}

