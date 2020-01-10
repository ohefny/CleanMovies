package com.example.swvlmovies.modules.movies.features.details.domain

import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Flowable

interface MovieDetailsRepository {
    fun getMoviePhotos(movie: Movie): Flowable<String>
}
