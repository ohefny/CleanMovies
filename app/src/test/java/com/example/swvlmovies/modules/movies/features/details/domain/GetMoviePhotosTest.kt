package com.example.swvlmovies.modules.movies.features.details.domain

import android.content.pm.ModuleInfo
import com.example.swvlmovies.modules.movies.features.search.domain.MoviesSearchRepository
import com.example.swvlmovies.modules.movies.features.search.domain.ValidateGenreSearch
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetMoviePhotosTest{

    @Mock
    lateinit var repository: MovieDetailsRepository
    @InjectMocks
    lateinit var getMoviePhotos: GetMoviePhotos
    private val movie = Movie(cast = emptyList(), genres = emptyList(), rating = 2, title = "", year = 2019)
    private val photosList = listOf("photo1","photo2","photo3","photo4")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(repository.getMoviePhotos(any())).thenReturn(Flowable.fromIterable(photosList))
    }
    @Test
    fun `when GetMoviePhotos_build is called then repository_getMoviesPhotos is called `(){
        getMoviePhotos.build(movie).test()
        verify(repository).getMoviePhotos(any())
    }
    @Test
    fun `when GetMoviePhotos_build is called then the same photos from repo is emitted `(){
        getMoviePhotos.build(movie).test().assertValue(photosList)

    }
}