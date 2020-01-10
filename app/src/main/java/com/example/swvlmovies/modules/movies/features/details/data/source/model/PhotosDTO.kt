package com.example.swvlmovies.modules.movies.features.details.data.source.model

import androidx.room.Entity
import com.example.swvlmovies.core.data.PHOTOS_TABLE

@Entity(primaryKeys = ["movieId"],tableName = PHOTOS_TABLE)
data class PhotosDTO(val movieId:String, val photosUrl:List<String>)