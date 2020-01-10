package com.example.swvlmovies.modules.movies.search.domain

import com.example.swvlmovies.base.domian.SingleUseCase
import com.example.swvlmovies.modules.movies.search.domain.enitiy.MoviesOfYear
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class SearchForMoviesByGenre @Inject constructor(
    private val repository: MoviesSearchRepository,
    private val validateGenreSearch: ValidateGenreSearch
) : SingleUseCase<String, List<MoviesOfYear>>() {
    override fun build(params: String): Single<List<MoviesOfYear>> {
        return validateGenreSearch.build(params)
            .flatMap { getMovies(params) }
    }

    private fun getMovies(params: String): Single<List<MoviesOfYear>> {
        return repository.getMoviesWithGenre(params).flattenAsFlowable { it }
            .groupBy { it.year }
            .distinct { it.key }
            .flatMapSingle {
                it.sorted { movie, movie2 -> movie2.rating.compareTo(movie.rating) } //descending sorting
                .take(PER_YEAR_LIMIT)
                .toList()
            }
            .map { MoviesOfYear(it[0].year, it) }
            .doOnNext { Timber.d("getMovies: $it") }
            .toList()
    }


    companion object {
        const val PER_YEAR_LIMIT = 5L
    }
}