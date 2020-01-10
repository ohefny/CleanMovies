package com.example.swvlmovies.modules.movies.features.details.data.source.remote

import com.example.swvlmovies.BuildConfig
import com.example.swvlmovies.modules.movies.features.details.data.source.model.PhotosResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviePhotosAPI{
    @GET("services/rest/?method=flickr.photos.​search&format=json&nojsoncallback=​1​")
    fun getPhotos(@Query("text")title:String,
                  @Query("page")page:Int=1,
                  @Query("per_page")count:Int=10,
                  @Query("api_key")key:String=BuildConfig.FLICKR_KEY): Single<PhotosResponseWrapper>

}