package com.example.swvlmovies.modules.movies.search.presentaion

sealed class CategorizedMovieUI {
    data class YearUI(val value:Int): CategorizedMovieUI()
    data class MovieUI(val idx:Int,val title:String,val year:Int):CategorizedMovieUI()
}