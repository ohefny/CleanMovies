package com.example.swvlmovies.modules.splash.di

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.core.data.SwvlMoviesDB
import com.example.swvlmovies.core.di.modules.ActivityScope
import com.example.swvlmovies.core.di.modules.ViewModelKey
import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.splash.data.MoviesPreparationRepositoryImpl
import com.example.swvlmovies.modules.splash.domain.MoviesPreparationRepository
import com.example.swvlmovies.modules.splash.presentation.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap


@Module
abstract class SplashModule {
    @Binds
    @ActivityScope
    abstract fun provideMoviesPreparationRepository(impl: MoviesPreparationRepositoryImpl): MoviesPreparationRepository
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    @ActivityScope
    internal abstract fun provideSplashModuleViewModel(viewModel: SplashViewModel): ViewModel
    @Module
    companion object{
        @Provides
        @JvmStatic
        @ActivityScope
        fun provideMoviesDAO(db:SwvlMoviesDB): MoviesDAO = db.moviesDao()

        @Provides
        @JvmStatic
        @ActivityScope
        fun provideAssetsManager(context: Context): AssetManager = context.assets
    }

}
