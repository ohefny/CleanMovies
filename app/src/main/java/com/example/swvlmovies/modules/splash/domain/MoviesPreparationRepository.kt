package com.example.swvlmovies.modules.splash.domain

import io.reactivex.Completable
import io.reactivex.Single

interface MoviesPreparationRepository {
    fun isMoviesPrepared(): Single<Boolean>
    fun prepareMovies(): Completable
}