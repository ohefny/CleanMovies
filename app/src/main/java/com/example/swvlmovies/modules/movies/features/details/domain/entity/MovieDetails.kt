package com.example.swvlmovies.modules.movies.features.details.domain.entity

import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie

data class MovieDetails(val movie: Movie, val photosUrl:List<String>)