package com.example.swvlmovies.modules.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swvlmovies.core.data.MOVIE_TABLE
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.common.data.local.models.MovieDTO
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieDTO>): Completable
    @Query("SELECT COUNT(*) FROM $MOVIE_TABLE")
    fun getMoviesCount():Single<Int>
    @Query("SELECT * FROM $MOVIE_TABLE WHERE genres LIKE '%'||:movieGenre||'%' ORDER BY year,rating DESC")
    fun getMoviesByGenre(movieGenre:String):Single<List<MovieDTO>>
    @Query("SELECT genres FROM $MOVIE_TABLE")
    fun getGenres(): Single<List<String>>
    @Query("SELECT * FROM $MOVIE_TABLE WHERE title=:title AND year=:year")
    fun getMovieByNameAndYear(year: Int, title: String): Single<MovieDTO>
}