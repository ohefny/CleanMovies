package com.example.swvlmovies.modules.movies.features.details.domain

import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Single

interface MovieDetailsRepository {
    fun getMoviePhotos(movie: Movie): Single<List<String>>
    fun getMovieFromID(movieID: MovieIDParam):Single<Movie>
}
