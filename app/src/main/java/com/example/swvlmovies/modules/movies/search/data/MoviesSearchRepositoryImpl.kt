package com.example.swvlmovies.modules.movies.search.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.common.data.local.models.MovieDTO
import com.example.swvlmovies.modules.common.data.local.serialization_adapters.DELIMITER
import com.example.swvlmovies.modules.movies.search.data.cache.MoviesCacheDS
import com.example.swvlmovies.modules.movies.search.domain.MoviesSearchRepository
import com.example.swvlmovies.modules.movies.search.domain.enitiy.Movie
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class MoviesSearchRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val moviesCacheDS: MoviesCacheDS
) : MoviesSearchRepository {
    override fun getMoviesWithGenre(genre: String): Single<List<Movie>> {
        return moviesDAO.getMoviesByGenre(genre)
            .flattenAsFlowable { it }
            .map { it.toDomain() }
            .toList()
    }

    override fun getGenres(): Flowable<String> {
        return moviesCacheDS.getGenres()
            .switchIfEmpty(Flowable.defer(::getGenresFromLocal))
            .map { it.name }
    }

    private fun getGenresFromLocal(): Flowable<GenreDTO> {
        return moviesDAO.getGenres()
            .flattenAsFlowable { it }
            .flatMapIterable { it.split(DELIMITER) }
            .map { it.trim() }
            .distinct()
            .map{GenreDTO(it)}
            .toList()//convert to toList so that we insert all available genres
            .doOnSuccess { moviesCacheDS.addGenres(it) }
            .flattenAsFlowable { it }
    }
}

private fun MovieDTO.toDomain():Movie = Movie(
    cast = cast?.map { it.name } ?: emptyList(),
    genres = genres!!.map { it.name },
    year = year,
    title = title,
    rating = rating
)
