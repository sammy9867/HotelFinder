package com.thesis.hotelfinder.api.network

enum class Status{
    SUCCESS,
    ERROR,
    LOADING;

    fun isSuccessful() = this == SUCCESS
    fun isError() = this == ERROR
    fun isLoading() = this == LOADING
}