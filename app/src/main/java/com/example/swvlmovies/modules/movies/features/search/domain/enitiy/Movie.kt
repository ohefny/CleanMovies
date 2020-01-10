package com.example.swvlmovies.modules.movies.features.search.domain.enitiy

data class Movie(val cast: List<String>,
                 val genres: List<String>,
                 val rating: Int,
                 val title: String,
                 val year: Int)