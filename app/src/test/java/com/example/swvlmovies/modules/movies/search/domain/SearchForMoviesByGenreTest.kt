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
    val movieClone=Movie(emptyList(), genresList, 1, "some title10", 1992)
    private val movies = listOf(
        movieClone
        , movieClone.copy(rating = 2,title = movieClone.title+"1")
        , movieClone.copy(rating = 3,title = movieClone.title+"2")
        , movieClone.copy(rating = 4,title = movieClone.title+"3")
        , movieClone.copy(rating = 5,title = movieClone.title+"4")
        , movieClone.copy(rating = 6,title = movieClone.title+"5")
        , movieClone.copy(rating = 6,title = movieClone.title+"6")
        , movieClone.copy(rating = 6,title = movieClone.title+"7")
        , movieClone.copy(rating = 6,title = movieClone.title+"8")
        , movieClone.copy(rating = 6,title = movieClone.title+"9")
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
        val firstYear = searchForMoviesByGenre.build("action")
            .test()
            .values()[0][0]//take first group
        assertEquals(PER_YEAR_LIMIT, firstYear.movies.size.toLong())
    }

    @Test
    fun `given listed genre when SearchForMoviesByGenre_build is called then only limited top rated movies is emitted`() {
        val firstYear = searchForMoviesByGenre.build("action")
            .test()
            .values()[0][0]//take first group
        for (i in 0..4)
            assertEquals(6, firstYear.movies[i].rating)
    }

    @Test
    fun `given movies with same year and more than the limit when SearchForMoviesByGenre_build is called then only one group is emitted for this year`() {
        val years = searchForMoviesByGenre.build("action")
            .test()
            .values()[0]
        val distinctGroups = years.distinctBy { it.year }
        assertEquals(years.size, distinctGroups.size)
    }
    @Test
    fun `given movies with differnt years when SearchForMoviesByGenre_build is called then groups with the same year number are emitted`() {
        val moviesWithDifferentYears= mutableListOf<Movie>().apply {addAll(movies)}
        moviesWithDifferentYears.add(movieClone.copy(year = 2000))
        moviesWithDifferentYears.add(movieClone.copy(year = 2002)) //now years are 1998 2000 2002
        whenever(repository.getMoviesWithGenre(any())).thenReturn(Flowable.fromIterable(moviesWithDifferentYears).toList())
        val years = searchForMoviesByGenre.build("action")
            .test()
            .values()[0]
        assertEquals(3,years.size)
    }
    @Test
    fun `given movies of year less than limit when SearchForMoviesByGenre_build is called then all those movies are emitted`() {
        val moviesWithFewerMovies= mutableListOf<Movie>()
        moviesWithFewerMovies.add(movieClone.copy(year = 2000))
        moviesWithFewerMovies.add(movieClone.copy(year = 2000)) //only 2 items of year 2000
        whenever(repository.getMoviesWithGenre(any())).thenReturn(Flowable.fromIterable(moviesWithFewerMovies).toList())
        val year2000Group = searchForMoviesByGenre.build("action")
            .test()
            .values()[0][0]//first group
        assertEquals(2,year2000Group.movies.size)
    }

}