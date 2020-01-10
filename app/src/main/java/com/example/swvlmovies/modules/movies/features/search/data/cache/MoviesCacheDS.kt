package com.example.swvlmovies.modules.movies.features.search.data.cache

import com.example.swvlmovies.modules.common.data.local.models.ActorDTO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import io.reactivex.Flowable
import javax.inject.Inject

class MoviesCacheDS @Inject constructor() {
    private val availableGenres: MutableSet<GenreDTO> = mutableSetOf()
    private val availableActors: MutableSet<ActorDTO> = mutableSetOf()
    fun addGenres(genres:  List<GenreDTO>) = availableGenres.addAll(genres)

    fun addActors(actors: List<ActorDTO>)= availableActors.addAll(actors)

    fun getActors(): Flowable<ActorDTO> = Flowable.fromIterable(availableActors)

    fun getGenres(): Flowable<GenreDTO> = Flowable.fromIterable(availableGenres)
}