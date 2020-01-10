package com.example.swvlmovies.modules.movies.features.details.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.base.domian.ThreadExecutor
import com.example.swvlmovies.base.presentation.PostExecutionThread
import com.example.swvlmovies.core.data.Resource
import com.example.swvlmovies.core.extention.addTo
import com.example.swvlmovies.core.extention.flatMapToResource
import com.example.swvlmovies.core.extention.publishResource
import com.example.swvlmovies.modules.movies.features.details.domain.GetMovieDetails
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieDetails
import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread
) : ViewModel() {
    private val _movieDetailsResource: MutableLiveData<Resource<MovieDetailsUIModel>> =
        MutableLiveData()
    val movieDetailsResource: LiveData<Resource<MovieDetailsUIModel>> = _movieDetailsResource
    private val compositeDisposable: CompositeDisposable =
        CompositeDisposable() //could be moved to parent class

    fun loadMovieDetails(movieTitle: String, movieYear: Int) {
        getMovieDetails.build(MovieIDParam(movieTitle, movieYear))
            .map(MovieDetails::toUI)
            .flatMapToResource()
            .publishResource(
                _movieDetailsResource,
                threadExecutor = threadExecutor,
                postExecutionThread = postExecutionThread
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}

private fun MovieDetails.toUI() = with(movie) {
    MovieDetailsUIModel(
        "$title ($year)",
        cast.joinToString(", "),
        genres.joinToString(", "),
        photosUrl
    )
}