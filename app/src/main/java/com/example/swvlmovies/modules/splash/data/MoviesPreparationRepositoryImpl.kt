package com.example.swvlmovies.modules.splash.data

import com.example.swvlmovies.modules.splash.domain.MoviesPreparationRepository
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesPreparationRepositoryImpl @Inject constructor():MoviesPreparationRepository{
    override fun isMoviesPrepared(): Single<Boolean> {
        return Single.just(false)
    }
    override fun prepareMovies(): Completable {
        Timber.d("prepareMovies() called")
        return Completable.timer(6000, TimeUnit.MILLISECONDS)
    }

}