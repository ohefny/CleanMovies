package com.example.swvlmovies.modules.movies.features.details.domain

import com.example.swvlmovies.base.domian.SingleUseCase
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieDetails
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import io.reactivex.Single
import javax.inject.Inject

class GetMovieDetails @Inject constructor(private val repository: MovieDetailsRepository) :
    SingleUseCase<MovieIDParam, MovieDetails>() {
    override fun build(params: MovieIDParam): Single<MovieDetails> = with(repository) {
        getMovieFromID(params)
            .flatMap { movie -> getMoviePhotos(movie).map {
                MovieDetails(
                    movie,
                    it
                )
            } }

    }
}