package com.example.swvlmovies.modules.splash.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.splash.data.source.AssetsDS
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesPreparationRepositoryTest {
    @Mock
    lateinit var moviesDAO: MoviesDAO
    @Mock
    lateinit var mockedAssetsDS: AssetsDS
    lateinit var repository: MoviesPreparationRepositoryImpl

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = spy(MoviesPreparationRepositoryImpl(assetsDS = mockedAssetsDS, moviesDAO = moviesDAO))
    }

    @Test
    fun `when repository isMoviesPrepared called then moviesDAO's getMoviesCount is called`() {
        whenever(moviesDAO.getMoviesCount()).thenReturn(Single.just(1))
        repository.isMoviesPrepared().test()
        verify(moviesDAO).getMoviesCount()
    }

    @Test
    fun `when repository's prepareMovies called then assetsDS's loadMovies is called`() {
        whenever(mockedAssetsDS.loadMovies()).thenReturn(Single.just(emptyList()))
        repository.prepareMovies().test()
        verify(mockedAssetsDS).loadMovies()
    }
    @Test
    fun `given no inserted movies when repository's isMoviesPrepared called then false is returned`(){
        whenever(moviesDAO.getMoviesCount()).thenReturn(Single.just(0))
        repository.isMoviesPrepared().test().assertValue(false)
    }
    @Test
    fun `given inserted movies when repository's isMoviesPrepared called then true is returned`(){
        whenever(moviesDAO.getMoviesCount()).thenReturn(Single.just(1))
        repository.isMoviesPrepared().test().assertValue(true)
    }
}