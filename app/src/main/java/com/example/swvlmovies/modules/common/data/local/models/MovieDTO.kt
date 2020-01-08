package com.example.swvlmovies.modules.common.data.local.models

import androidx.room.Entity
import com.example.swvlmovies.core.data.MOVIE_TABLE

@Entity(tableName = MOVIE_TABLE ,primaryKeys = ["title","year"])
data class MovieDTO(
    val cast: List<String>,
    val genres: List<String>,
    val rating: Int,
    val title: String,
    val year: Int
)