package com.example.swvlmovies.modules.splash.domain

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import java.util.concurrent.TimeUnit

class PrepareMoviesDataTest{
    @Mock
    lateinit var repository: MoviesPreparationRepository

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `given movies is not prepared when PrepareMoviesData build is called then prepareMovies is called`() {
        whenever(repository.isMoviesPrepared()).thenReturn(Single.just(false))
        PrepareMoviesData(repository).build(Unit).test()
        verify(repository).prepareMovies()
    }
    @Test
    fun `given movies is prepared when PrepareMoviesData build is called then prepareMovies is not called`() {
        whenever(repository.isMoviesPrepared()).thenReturn(Single.just(true))
        PrepareMoviesData(repository).build(Unit).test()
        verify(repository,Times(0)).prepareMovies()
    }
    @Test
    fun `given movies is not prepared when PrepareMoviesData build then it will complete after preparation is finished`() {
        val preparationTime=7000L
        val testScheduler = TestScheduler()
        whenever(repository.isMoviesPrepared()).thenReturn(Single.just(false))
        whenever(repository.prepareMovies()).thenReturn(Completable.timer(preparationTime,TimeUnit.MILLISECONDS, testScheduler))
        val testObserver = PrepareMoviesData(repository).build(Unit).test()
        testObserver.assertNotComplete()
        testScheduler.advanceTimeTo(5000,TimeUnit.MILLISECONDS)
        testObserver.assertNotComplete()
        testScheduler.advanceTimeTo(preparationTime,TimeUnit.MILLISECONDS)
        testObserver.assertComplete()
    }

}