package com.example.swvlmovies.modules.movies.features.details.data.source.model

data class PhotosResponseWrapper(val photos: PhotosResponse)
data class PhotosResponse(val photo: List<PhotoResponse>)
data class PhotoResponse(
    val farm: Int,
    val id: String,
    val secret: String,
    val server: String)
