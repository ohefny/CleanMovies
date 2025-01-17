package com.example.swvlmovies.modules.movies.features.search.data

import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.common.data.local.serialization_adapters.DELIMITER
import com.example.swvlmovies.modules.movies.features.search.data.cache.MoviesCacheDS
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
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
    val genres:List<String> = mutableListOf("action","action","war","drama","biography")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MoviesSearchRepositoryImpl(moviesDAO,mockedCacheDS)
    }
    @Test
    fun `when repository getGenres called then MoviesCacheDS's addGenre is called with the same number of distinct genres count`() {
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genres))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.empty<GenreDTO>())
        repository.getGenres().test()
        verify(mockedCacheDS).addGenres(any())
    }
    @Test
    fun `when repository getGenres called then only distinct genres are returned`() {
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genres))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.empty<GenreDTO>())
        val distinctGenreSize = genres.distinct().size
        repository.getGenres().test().assertValueCount(distinctGenreSize)
    }
    @Test
    fun `given comma seperated generes when repository getGenres called then genres are flatten by removing comma and distinc`() {
        val genresSeperateByComma:List<String> = mutableListOf(genres.joinToString(DELIMITER))
        whenever(moviesDAO.getGenres()).thenReturn(Single.just(genresSeperateByComma))
        whenever(mockedCacheDS.getGenres()).thenReturn(Flowable.empty<GenreDTO>())
        repository.getGenres().test().assertValueCount(4) //total count is 5 with 1 duplicate
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