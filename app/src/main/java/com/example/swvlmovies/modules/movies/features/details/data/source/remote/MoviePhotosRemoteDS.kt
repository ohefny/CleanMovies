package com.example.swvlmovies.modules.movies.features.details.data.source.remote

import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotoResponse
import io.reactivex.Flowable
import javax.inject.Inject

class MoviePhotosRemoteDS @Inject constructor(val moviePhotosAPI: MoviePhotosAPI){
    fun getMoviePhotos(movieTitle:String,movieYear:String): Flowable<String> {
        return moviePhotosAPI.getPhotos(title = "$movieTitle $movieTitle")
            .map { it.photos }
            .flattenAsFlowable { it.photo }
            .map { it.toUrl() }
    }
    private fun PhotoResponse.toUrl() = "http//farm​$farm​.static.flickr.com/​$server/​$id​_​$secret​.jpg"

}