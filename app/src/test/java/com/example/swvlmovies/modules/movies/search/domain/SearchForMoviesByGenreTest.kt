package com.example.swvlmovies.modules.movies.search.domain

import com.example.swvlmovies.modules.movies.search.domain.SearchForMoviesByGenre.Companion.PER_YEAR_LIMIT
import com.example.swvlmovies.modules.movies.search.domain.enitiy.Movie
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times

class SearchForMoviesByGenreTest {
    @Mock
    lateinit var repository: MoviesSearchRepository
    lateinit var validateGenreSearch: ValidateGenreSearch
    lateinit var searchForMoviesByGenre: SearchForMoviesByGenre
    private val genresList = listOf("action", "drama", "war", "crime")
    private val movies = listOf(
        Movie(emptyList(), genresList, 1, "some title10", 1992)
        , Movie(emptyList(), genresList, 2, "some title9", 1992)
        , Movie(emptyList(), genresList, 3, "some title8", 1992)
        , Movie(emptyList(), genresList, 4, "some title7", 1992)
        , Movie(emptyList(), genresList, 5, "some title6", 1992)
        , Movie(emptyList(), genresList, 6, "some title5", 1992)
        , Movie(emptyList(), genresList, 6, "some title4", 1992)
        , Movie(emptyList(), genresList, 6, "some title3", 1992)
        , Movie(emptyList(), genresList, 6, "some title2", 1992)
        , Movie(emptyList(), genresList, 6, "some title1", 1992)
    )

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        validateGenreSearch = spy(ValidateGenreSearch(repository))
        searchForMoviesByGenre = SearchForMoviesByGenre(repository, validateGenreSearch)
        whenever(repository.getGenres()).thenReturn(Flowable.fromIterable(genresList))
        whenever(repository.getMoviesWithGenre(any())).thenReturn(Flowable.fromIterable(movies).toList())
    }

    @Test
    fun `when SearchForMoviesByGenre build is called then ValidateGenreSearch_build is called with the same genre`() {
        searchForMoviesByGenre.build("genre1").test()
        verify(validateGenreSearch).build("genre1")
    }

    @Test
    fun `given non listed genre when SearchForMoviesByGenre_build is called then MoviesSearchRepository_getMovies not called`() {
        searchForMoviesByGenre.build("non_listed_genre").test()
        verify(repository, Times(0)).getMoviesWithGenre("")
    }

    @Test
    fun `given listed genre when SearchForMoviesByGenre_build is called then MoviesSearchRepository_getMovies is called with the same genre`() {
        searchForMoviesByGenre.build("action").test()
        verify(repository).getMoviesWithGenre("action")
    }

    @Test
    fun `given listed genre when SearchForMoviesByGenre_build is called then only limited movies is emitted`() {
        val movieGroup = searchForMoviesByGenre.build("action")
            .test()
            .values()[0][0]//take first group
        assertEquals(PER_YEAR_LIMIT, movieGroup.movies.size.toLong())
    }

    @Test
    fun `given listed genre when SearchForMoviesByGenre_build is called then only limited top rated movies is emitted`() {
        val movieGroup = searchForMoviesByGenre.build("action")
            .test()
            .values()[0][0]//take first group
        for (i in 0..4)
            assertEquals(6, movieGroup.movies[i].rating)
    }

    @Test
    fun `given movies with same year and more than the limit when SearchForMoviesByGenre_build is called then only one group is emitted for this year`() {
        val movieGroups = searchForMoviesByGenre.build("action")
            .test()
            .values()[0]//take first group
        val distinctGroups = movieGroups.distinctBy { it.year }
        assertEquals(movieGroups.size, distinctGroups.size)
    }

}