package com.example.swvlmovies.modules.movies.search.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.movies.search.data.cache.MoviesCacheDS
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times

class MoviesSearchRepositoryImplTest {
    @Mock
    lateinit var moviesDAO: MoviesDAO
    @Mock
    lateinit var mockedCacheDS: MoviesCacheDS
    lateinit var repository: MoviesSearchRepositoryImpl
    val genres:List<String> = mutableListOf("action","Action","war","drama","biography")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MoviesSearchRepositoryImpl(moviesDAO,mockedCacheDS)
    }
    //todo investigate why addGenre is not called
    @Test
    fun `when repository getGenres called then MoviesCacheDS's addGenre is called with the same number of distinct genres count`() {
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genres))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.fromIterable(emptyList<GenreDTO>()))
        repository.getGenres().test()
        verify(mockedCacheDS,Times(genres.distinct().size)).addGenre(any())
    }
    @Test
    fun `when repository getGenres called then MoviesCacheDS's getGenres is called `() {
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.fromIterable(listOf(GenreDTO("action"))))
        repository.getGenres().test()
        verify(mockedCacheDS).getGenres()
    }
    @Test
    fun `given not cached genres when repository getGenres called then MoviesDAO's getGenres is called `() {
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genres))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.fromIterable(emptyList<GenreDTO>()))
        repository.getGenres().test()
        verify(moviesDAO).getGenres()
    }
    @Test
    fun `given cached genres when repository getGenres called then MoviesDAO's getGenres is not called `() {
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genres))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.fromIterable(listOf(GenreDTO("action"))))
        repository.getGenres().test()
        verify(mockedCacheDS).getGenres() //cached called once
        verify(moviesDAO,Times(0)).getGenres()//local not called
    }

}