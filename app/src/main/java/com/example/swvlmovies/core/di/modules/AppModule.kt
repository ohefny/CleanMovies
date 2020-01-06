package com.example.swvlmovies.core.di.modules

import android.content.Context
import com.example.swvlmovies.core.application.SwvlMoviesApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: SwvlMoviesApplication): Context = application.applicationContext

}
