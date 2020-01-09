package com.example.swvlmovies.modules.common.data.local.serialization_adapters

import androidx.room.TypeConverter
import com.example.swvlmovies.modules.common.data.local.models.ActorDTO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO

class ActorListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<ActorDTO> {
        return value.split(DELIMITER).map(::ActorDTO)
    }

    @TypeConverter
    fun fromStringList(list: List<ActorDTO>): String {
        return list.joinToString(separator = DELIMITER) { it.name }
    }
}

class GenreListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<GenreDTO> {
        return value.split(DELIMITER).map(::GenreDTO)
    }

    @TypeConverter
    fun fromStringList(list: List<GenreDTO>): String {
        return list.joinToString(separator = DELIMITER) { it.name }
    }
}
const val DELIMITER = " , "
