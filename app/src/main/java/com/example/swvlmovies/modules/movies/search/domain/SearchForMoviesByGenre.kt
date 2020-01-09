package com.example.swvlmovies.modules.movies.search.domain

import com.example.swvlmovies.base.domian.SingleUseCase
import com.example.swvlmovies.modules.movies.search.domain.enitiy.Movie
import io.reactivex.Single
import javax.inject.Inject

class SearchForMoviesByGenre @Inject constructor(
    private val repository: MoviesSearchRepository,
    private val validateGenreSearch: ValidateGenreSearch
) :
    SingleUseCase<String, List<SearchForMoviesByGenre.MoviesGroupByYear>>() {
    override fun build(params: String): Single<List<MoviesGroupByYear>> {
        return validateGenreSearch.build(params)
            .flatMap { getMovies(params) }
    }

    private fun getMovies(params: String): Single<MutableList<MoviesGroupByYear>> {
        return repository.getMoviesWithGenre(params).flattenAsFlowable { it }
            .sorted { o1, o2 -> o2.rating.compareTo(o1.rating) } //descending sorting
            .groupBy { it.year }
            .distinct { it.key }
            .flatMapSingle {it.take(PER_YEAR_LIMIT).toList() }
            .map { MoviesGroupByYear(it[0].year, it) }
            .sorted { o1, o2 -> o1.year.compareTo(o2.year) }
            .toList()
    }

    data class MoviesGroupByYear(val year: Int, val movies: List<Movie>)

    companion object {
        const val PER_YEAR_LIMIT = 5L
    }
}