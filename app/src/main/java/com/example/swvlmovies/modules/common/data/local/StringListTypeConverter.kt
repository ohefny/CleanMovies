package com.example.swvlmovies.modules.common.data.local

import androidx.room.TypeConverter

class StringListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(separator = ",")
    }
}