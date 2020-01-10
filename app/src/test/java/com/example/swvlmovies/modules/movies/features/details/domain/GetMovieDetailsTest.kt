package com.example.swvlmovies.modules.movies.features.details.domain

import com.example.swvlmovies.modules.movies.features.details.domain.entity.MovieIDParam
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetMovieDetailsTest{

    @Mock
    lateinit var repository: MovieDetailsRepository
    @InjectMocks
    lateinit var getMovieDetails: GetMovieDetails
    private val movie = Movie(cast = emptyList(), genres = emptyList(), rating = 2, title = "title", year = 2020)
    private val movieID = MovieIDParam(title = "title", year = 2020)

    private val photosList = listOf("photo1","photo2","photo3","photo4")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(repository.getMoviePhotos(any())).thenReturn(Single.just(photosList))
        whenever(repository.getMovieFromID(any())).thenReturn(Single.just(movie))

    }
    @Test
    fun `when GetMovieDetails_build is called then repository_getMoviesPhotos is called `(){
        getMovieDetails.build(movieID).test()
        verify(repository).getMoviePhotos(any())
    }
    @Test
    fun `when GetMovieDetails_build is called then repository_getMovieFromID is called `(){
        getMovieDetails.build(movieID).test()
        verify(repository).getMovieFromID(any())
    }
    @Test
    fun `when GetMovieDetails_build is called then the same photos from repo is emitted `(){
        getMovieDetails.build(movieID).test().assertValue{
            it.photosUrl==photosList
        }

    }
}