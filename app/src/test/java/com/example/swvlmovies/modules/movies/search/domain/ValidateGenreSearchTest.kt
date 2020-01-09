package com.example.swvlmovies.modules.movies.search.domain

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ValidateGenreSearchTest {
    @Mock
    lateinit var repository: MoviesSearchRepository
    @InjectMocks
    lateinit var validateGenreSearch: ValidateGenreSearch
    private val genresList = listOf("action","drama","war","crime")
    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        whenever(repository.getGenres()).thenReturn(Flowable.fromIterable(genresList))
    }

    @Test
    fun `given non listed genre when ValidateGenreSearch build is called then IllegalArgumentException is emitted`() {
        validateGenreSearch.build("unlisted_genre").test().assertError(IllegalArgumentException::class.java)
    }
    @Test
    fun `given listed genre when ValidateGenreSearch build is called then True is emitted`() {
        validateGenreSearch.build("action").test().assertValue(true)
    }
    @Test
    fun `given listed genre with spaces when ValidateGenreSearch build is called then True is emitted`() {
        validateGenreSearch.build(" action ").test().assertValue(true)
    }
    @Test
    fun `given listed genre with non matching capes when ValidateGenreSearch build is called then True is emitted`() {
        validateGenreSearch.build(" aCtIon ").test().assertValue(true)
    }
}