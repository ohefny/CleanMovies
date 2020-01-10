package com.example.swvlmovies.modules.movies.features.details.data.source.local

import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotosDTO
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class PhotosLocalDS @Inject constructor(private val photosDAO: PhotosDAO){
    fun getMoviePhotos(movie: Movie): Flowable<List<String>> = with(photosDAO){
        getPhotos(movie.getID()).map {it.photosUrl}.toFlowable()
    }
    fun insertMoviePhotos(movie: Movie,photos:List<String>):Completable = with(photosDAO){
        insertPhotos(PhotosDTO(movie.getID(),photos))
    }

    private fun Movie.getID() = title+"_"+year
}