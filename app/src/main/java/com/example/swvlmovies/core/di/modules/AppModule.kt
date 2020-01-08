package com.example.swvlmovies.core.di.modules

import android.content.Context
import androidx.room.Room
import com.example.swvlmovies.core.application.SwvlMoviesApplication
import com.example.swvlmovies.core.data.SwvlMoviesDB
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: SwvlMoviesApplication): Context = application.applicationContext
    @Provides
    @Singleton
    internal fun provideRoomDB(applicationContext: Context): SwvlMoviesDB =
        Room.databaseBuilder(
            applicationContext,
            SwvlMoviesDB::class.java, "swvl-movie-db")
            .build()
    @Provides
    @Singleton
    internal fun provideGson():Gson = Gson()
}
