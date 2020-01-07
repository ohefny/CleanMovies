package com.example.swvlmovies.modules.splash.domain

import com.example.swvlmovies.base.domian.CompletableUseCase
import io.reactivex.Completable
import timber.log.Timber
import javax.inject.Inject

class PrepareMoviesData @Inject constructor(private val repository: MoviesPreparationRepository) :
    CompletableUseCase<Unit>() {
    override fun build(params: Unit): Completable {
        return repository.isMoviesPrepared().flatMapCompletable{
            if(it)
                Completable.complete()
            else
                repository.prepareMovies()
        }
    }
}