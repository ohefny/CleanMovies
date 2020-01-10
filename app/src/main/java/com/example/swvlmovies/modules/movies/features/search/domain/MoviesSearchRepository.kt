package com.example.swvlmovies.modules.movies.features.search.domain

import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Flowable
import io.reactivex.Single

interface MoviesSearchRepository {
    fun getGenres(): Flowable<String>
    fun getMoviesWithGenre(genre:String): Single<List<Movie>>
}