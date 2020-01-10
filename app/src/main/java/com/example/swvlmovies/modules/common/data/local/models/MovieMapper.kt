package com.example.swvlmovies.modules.common.data.local.models

import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie

fun MovieDTO.toDomain(): Movie = Movie(
    cast = cast?.map { it.name } ?: emptyList(),
    genres = genres!!.map { it.name },
    year = year,
    title = title,
    rating = rating
)
