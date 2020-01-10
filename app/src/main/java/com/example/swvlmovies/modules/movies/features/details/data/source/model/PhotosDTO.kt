package com.example.swvlmovies.modules.movies.features.details.data.source.model

import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.swvlmovies.core.data.PHOTOS_TABLE
import com.example.swvlmovies.modules.movies.features.details.data.source.local.PhotosTypeConverter

@Entity(primaryKeys = ["movieId"],tableName = PHOTOS_TABLE)
@TypeConverters(PhotosTypeConverter::class)
data class PhotosDTO(val movieId:String, val photosUrl:List<String>)