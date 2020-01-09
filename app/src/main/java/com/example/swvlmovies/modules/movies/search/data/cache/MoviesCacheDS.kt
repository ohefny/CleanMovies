package com.example.swvlmovies.modules.movies.search.data.cache

import com.example.swvlmovies.modules.common.data.local.models.ActorDTO
import com.example.swvlmovies.modules.common.data.local.models.GenreDTO
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import javax.inject.Inject

class MoviesCacheDS @Inject constructor() {
    private val availableGenres: MutableSet<GenreDTO> = mutableSetOf()
    private val availableActors: MutableSet<ActorDTO> = mutableSetOf()
    fun addGenres(genres:  List<GenreDTO>) = availableGenres.addAll(genres)
    fun addGenre(genre: GenreDTO) = availableGenres.add(genre)

    fun addActors(actors: List<ActorDTO>)= availableActors.addAll(actors)

    fun getActors(): Flowable<ActorDTO> = Flowable.fromIterable(availableActors)

    fun getGenres(): Flowable<GenreDTO> = Flowable.fromIterable(availableGenres)
}