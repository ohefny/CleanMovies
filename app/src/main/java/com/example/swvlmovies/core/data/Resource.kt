package com.example.swvlmovies.core.data

sealed class Resource<out T> {
    class Loading<T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()
}


fun <T,U> Resource<T>.mapTo(mapper:(T)->U):Resource<U>{
    return when {
        this is Resource.Success<T> -> Resource.Success(mapper(data))
        this is Resource.Loading -> Resource.Loading()
        else -> Resource.Failure((this as Resource.Failure).throwable)
    }
}