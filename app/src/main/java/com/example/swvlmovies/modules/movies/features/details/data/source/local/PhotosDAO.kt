package com.example.swvlmovies.modules.movies.features.details.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.swvlmovies.core.data.PHOTOS_TABLE
import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotosDTO
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface PhotosDAO {
    @Query("SELECT * FROM $PHOTOS_TABLE WHERE movieId = movieId")
    fun getPhotos(movieID:String): Maybe<PhotosDTO>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhotos(photos:PhotosDTO): Completable

}