package com.example.swvlmovies.modules.splash.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.splash.data.source.AssetsDS
import com.example.swvlmovies.modules.splash.domain.MoviesPreparationRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class MoviesPreparationRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val assetsDS: AssetsDS
) : MoviesPreparationRepository {

    override fun isMoviesPrepared(): Single<Boolean> = moviesDAO.getMoviesCount().map { it > 0 }


    override fun prepareMovies(): Completable =
        assetsDS.loadMovies().flattenAsFlowable { it }
            .map { movie ->
                val lowerCaseGenres = movie.genres?.map { it.copy(name = it.name.toLowerCase()) }
                movie.copy(genres = lowerCaseGenres)
            }
            .toList()
            .flatMapCompletable(moviesDAO::insertMovies)


}