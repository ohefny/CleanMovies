package com.example.swvlmovies.modules.movies.features.details.data.source.local

import androidx.room.TypeConverter
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import com.example.swvlmovies.modules.common.data.local.serialization_adapters.DELIMITER
import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotosDTO

class PhotosTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(DELIMITER)
    }
    @TypeConverter
    fun fromGenreList(list: List<String>): String {
        return list.joinToString(separator = DELIMITER)
    }
}