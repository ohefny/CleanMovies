package com.example.swvlmovies.modules.splash.data.source

import android.content.res.AssetManager
import com.example.swvlmovies.modules.common.data.local.models.MovieDTO
import com.google.gson.Gson
import io.reactivex.Single
import javax.inject.Inject
private data class MoviesWrapper(val movies: List<MovieDTO>)
class AssetsDS @Inject constructor(private val assetManager: AssetManager, private val gson: Gson) {
    fun loadMovies(): Single<List<MovieDTO>> {
        return extractAssetContent(MOVIES_FILE_NAME)
            .map { gson.fromJson(it, MoviesWrapper::class.java) }
            .map { it.movies }
    }

    private fun extractAssetContent(filename: String): Single<String> {
        return Single.fromCallable {
            assetManager.open(filename).bufferedReader().use { it.readText() }
        }
    }


    companion object {
        const val MOVIES_FILE_NAME = "movies.json"
    }

}