package com.example.swvlmovies.modules.movies.features.search.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.base.domian.ThreadExecutor
import com.example.swvlmovies.base.presentation.PostExecutionThread
import com.example.swvlmovies.core.data.Resource
import com.example.swvlmovies.core.extention.addTo
import com.example.swvlmovies.core.extention.flatMapToResource
import com.example.swvlmovies.core.extention.publishResource
import com.example.swvlmovies.core.presentation.SingleLiveEvent
import com.example.swvlmovies.modules.movies.features.search.domain.SearchForMoviesByGenre
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.MoviesOfYear
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesSearchViewModel @Inject constructor(
    private val searchForMoviesByGenre: SearchForMoviesByGenre,
    val threadExecutor: ThreadExecutor,
    val postExecutionThread: PostExecutionThread
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _categorizedGenreMovies = MutableLiveData<Resource<List<CategorizedMovieUI>>>()
    val categorizedGenreMovies: LiveData<Resource<List<CategorizedMovieUI>>> =
        _categorizedGenreMovies
    private val _clickedMovie = SingleLiveEvent<Movie>()
    val clickedMovie: LiveData<Movie> = _clickedMovie
    var lastSearchMovies: List<MoviesOfYear> = emptyList()
    fun searchGenreMovies(genre: String) {
        searchForMoviesByGenre.build(genre)
            .doOnSuccess { lastSearchMovies = it }
            .flattenAsFlowable { it }
            .map(MoviesOfYear::toUI)
            .toList()
            .map { it.flatten() }
            .flatMapToResource()
            .publishResource(
                _categorizedGenreMovies,
                threadExecutor = threadExecutor,
                postExecutionThread = postExecutionThread
            )
            .addTo(compositeDisposable)
        lastSearchMovies = emptyList() //removing last result
    }

    fun onMovieClicked(movieUI: CategorizedMovieUI.MovieUI) {
        val moviesOfYear = lastSearchMovies.firstOrNull { it.year == movieUI.year }
        if (moviesOfYear != null)
            _clickedMovie.value = moviesOfYear.movies[movieUI.idx]
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

private fun MoviesOfYear.toUI(): List<CategorizedMovieUI> {
    val ls = mutableListOf<CategorizedMovieUI>()
    ls.add(CategorizedMovieUI.YearUI(year))
    movies.forEachIndexed { index, movie ->
        ls.add(
            CategorizedMovieUI.MovieUI(
                index,
                movie.title,
                year
            )
        )
    }
    return ls
}
