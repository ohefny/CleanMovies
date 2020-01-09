package com.example.swvlmovies.modules.movies.search.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.common.data.local.serialization_adapters.DELIMITER
import com.example.swvlmovies.modules.movies.search.data.cache.MoviesCacheDS
import com.example.swvlmovies.modules.movies.search.domain.MoviesSearchRepository
import com.example.swvlmovies.modules.movies.search.domain.enitiy.Movie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MoviesSearchRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val moviesCacheDS: MoviesCacheDS
) : MoviesSearchRepository {
    override fun getMoviesWithGenre(genre: String): Single<List<Movie>> {
        return Single.just(emptyList())
    }

    override fun getGenres(): Flowable<String> {
        return moviesCacheDS.getGenres()
            .switchIfEmpty { getGenresFromLocal() }
            .map { it.name }
    }
    private fun getGenresFromLocal(): Flowable<GenreDTO> {
        return moviesDAO.getGenres()
            .flattenAsFlowable { it }
            .flatMapIterable { it.split(DELIMITER) }
            .map { it.trim() }
            .distinct()
            .map(::GenreDTO)
            .doOnNext{moviesCacheDS.addGenre(it)}
    }
}