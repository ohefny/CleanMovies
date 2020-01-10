package com.example.swvlmovies.modules.movies.features.details.domain

import com.example.swvlmovies.base.domian.SingleUseCase
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Single
import javax.inject.Inject

class GetMoviePhotos @Inject constructor(private val repository: MovieDetailsRepository) :
    SingleUseCase<Movie, List<String>>() {
    override fun build(params: Movie): Single<List<String>> = repository.getMoviePhotos(params)
}