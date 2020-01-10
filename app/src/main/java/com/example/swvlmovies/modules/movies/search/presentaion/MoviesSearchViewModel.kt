package com.example.swvlmovies.modules.movies.search.presentaion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.base.domian.ThreadExecutor
import com.example.swvlmovies.base.presentation.PostExecutionThread
import com.example.swvlmovies.core.data.Resource
import com.example.swvlmovies.core.extention.addTo
import com.example.swvlmovies.core.extention.flatMapToResource
import com.example.swvlmovies.core.extention.publishResource
import com.example.swvlmovies.modules.movies.search.domain.SearchForMoviesByGenre
import com.example.swvlmovies.modules.movies.search.domain.enitiy.Movie
import com.example.swvlmovies.modules.movies.search.domain.enitiy.MoviesOfYear
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MoviesSearchViewModel @Inject constructor(private val searchForMoviesByGenre: SearchForMoviesByGenre, val threadExecutor: ThreadExecutor, val postExecutionThread: PostExecutionThread) : ViewModel() {
    private val _categorizedGenreMovies = MutableLiveData<Resource<List<CategorizedMovieUI>>>()
    val categorizedGenreMovies: LiveData<Resource<List<CategorizedMovieUI>>> = _categorizedGenreMovies
    private val _clickedMovie = MutableLiveData<Movie>()
    val clickedMovie:LiveData<Movie> = _clickedMovie
    var lastSearchMovies:List<MoviesOfYear> = emptyList()
    private val compositeDisposable= CompositeDisposable()

    fun searchGenreMovies(genre:String){
        searchForMoviesByGenre.build(genre)
            .doOnSuccess { lastSearchMovies=it }
            .flattenAsFlowable { it }
            .map { it.toUI() }
            .toList()
            .map { it.flatten() }
            .flatMapToResource()
            .publishResource(_categorizedGenreMovies,threadExecutor=threadExecutor,postExecutionThread = postExecutionThread)
            .addTo(compositeDisposable)
    }
    fun onMovieClicked(movieUI:CategorizedMovieUI.MovieUI){
        _clickedMovie.value = lastSearchMovies.first { it.year == movieUI.year }
                .let { it.movies[movieUI.idx]}
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

private fun MoviesOfYear.toUI(): List<CategorizedMovieUI> {
    val ls=mutableListOf<CategorizedMovieUI>()
    ls.add(CategorizedMovieUI.YearUI(year))
    movies.forEachIndexed { index, movie -> ls.add(CategorizedMovieUI.MovieUI(index,movie.title,year))}
    return ls
}
