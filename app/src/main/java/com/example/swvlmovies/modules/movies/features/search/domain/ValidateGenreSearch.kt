package com.example.swvlmovies.modules.movies.features.search.domain

import com.example.swvlmovies.base.domian.SingleUseCase
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ValidateGenreSearch @Inject constructor(private val repository: MoviesSearchRepository) :
    SingleUseCase<String, Boolean>() {
    override fun build(params: String): Single<Boolean> =
        repository.getGenres().any { it == params.trim().toLowerCase() }
            .flatMap {
                if (it) Single.just(true)
                else Single.error(IllegalArgumentException("Not valid genre"))
            }
}