package com.example.swvlmovies.modules.movies.features.details.data.source.remote

import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotoResponse
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Flowable
import javax.inject.Inject

class MoviePhotosRemoteDS @Inject constructor(private val moviePhotosAPI: MoviePhotosAPI){
    fun getMoviePhotos(movie:Movie): Flowable<String> = with(moviePhotosAPI){
        return getPhotos(title = movie.getPhotoQuery())
            .map { it.photos }
            .flattenAsFlowable { it.photo }
            .map { it.toUrl() }
    }
    private fun Movie.getPhotoQuery() = "Movie $title $year"
    private fun PhotoResponse.toUrl() = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"

}