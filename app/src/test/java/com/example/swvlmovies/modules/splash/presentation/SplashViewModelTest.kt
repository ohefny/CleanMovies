package com.example.swvlmovies.modules.splash.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.swvlmovies.core.schedulers.RxImmediateSchedulerRule.Companion.postExecutionThread
import com.example.swvlmovies.core.schedulers.TestThreadExecutor
import com.example.swvlmovies.modules.splash.domain.MoviesPreparationRepository
import com.example.swvlmovies.modules.splash.domain.PrepareMoviesData
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import java.util.concurrent.TimeUnit

//todo solve problem of running all tests together first one fails
//todo remove FixMethodOrder
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SplashViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Mock
    lateinit var repository: MoviesPreparationRepository
    @InjectMocks
    lateinit var prepareMoviesData: PrepareMoviesData
    lateinit var spyPrepareMoviesData: PrepareMoviesData
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        spyPrepareMoviesData = spy(prepareMoviesData)

    }
    @Test
    fun `given movies is not prepared isDataPrepared will wait until preparation is finished`() {
        val preparationTime=7000L
        val testScheduler = TestScheduler()
        RxJavaPlugins.setInitComputationSchedulerHandler { _ -> testScheduler} //timer runs in computation by default add testScheduler to control timing
        whenever(repository.isMoviesPrepared()).thenReturn(Single.just(false))
        whenever(repository.prepareMovies()).thenReturn(Completable.timer(preparationTime, TimeUnit.MILLISECONDS))
        val splashViewModel = SplashViewModel(prepareMoviesData,
            TestThreadExecutor(), postExecutionThread)
        testScheduler.advanceTimeTo(SplashViewModel.SPLASH_DELAY_TIME,TimeUnit.MILLISECONDS) //timer is finished
        assert(splashViewModel.isDataReady.value==null) // no emission received
        testScheduler.advanceTimeTo(preparationTime+10000,TimeUnit.MILLISECONDS)
        assert(splashViewModel.isDataReady.value!=null)
    }
    @Test
    fun `given movies is prepared isDataPrepared will emmit after splash delay is finished`() {
        val testScheduler = TestScheduler()
        RxJavaPlugins.reset()
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> testScheduler} //timer runs in computation by default add testScheduler to control timing
        whenever(repository.isMoviesPrepared()).thenReturn(Single.just(true))
        val splashViewModel = SplashViewModel(prepareMoviesData,
            TestThreadExecutor(), postExecutionThread)
        verify(repository, Times(0)).prepareMovies()
        testScheduler.advanceTimeBy(SplashViewModel.SPLASH_DELAY_TIME-500,TimeUnit.MILLISECONDS) //timer is not finished
        assert(splashViewModel.isDataReady.value==null) // no emission received
        testScheduler.advanceTimeBy(SplashViewModel.SPLASH_DELAY_TIME,TimeUnit.MILLISECONDS)//timer is finished
        assert(splashViewModel.isDataReady.value!=null)
    }



}