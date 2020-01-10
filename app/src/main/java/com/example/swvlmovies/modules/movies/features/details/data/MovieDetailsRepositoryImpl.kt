package com.example.swvlmovies.modules.movies.features.details.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.MovieDTO
import com.example.swvlmovies.modules.common.data.local.models.toDomain
import com.example.swvlmovies.modules.movies.features.details.data.source.local.PhotosLocalDS
import com.example.swvlmovies.modules.movies.features.details.data.source.remote.MoviePhotosRemoteDS
import com.example.swvlmovies.modules.movies.features.details.domain.MovieDetailsRepository
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val photosLocalDS: PhotosLocalDS,
    private val photosRemoteDS: MoviePhotosRemoteDS
) : MovieDetailsRepository {
    override fun getMovieFromID(movieID: MovieIDParam): Single<Movie> =
        moviesDAO.getMovieByNameAndYear(year=movieID.year,title=movieID.title)
            .map(MovieDTO::toDomain)

    override fun getMoviePhotos(movie: Movie): Single<List<String>> {
        return photosLocalDS.getMoviePhotos(movie)
            .switchIfEmpty(Flowable.defer { getRemotePhotos(movie) })
            .toList()
    }

    private fun getRemotePhotos(movie: Movie): Flowable<String> = with(photosRemoteDS) {
        getMoviePhotos(movie).toList().toFlowable().flatMap { ls ->
            photosLocalDS.insertMoviePhotos(movie, ls)
                .andThen(Flowable.fromIterable(ls))
        }
    }
}