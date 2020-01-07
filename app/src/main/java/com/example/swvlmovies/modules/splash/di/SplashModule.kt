package com.example.swvlmovies.modules.splash.di

import androidx.lifecycle.ViewModel
import com.example.swvlmovies.core.di.modules.ActivityScope
import com.example.swvlmovies.core.di.modules.ViewModelKey
import com.example.swvlmovies.modules.splash.data.MoviesPreparationRepositoryImpl
import com.example.swvlmovies.modules.splash.domain.MoviesPreparationRepository
import com.example.swvlmovies.modules.splash.presentation.SplashViewModel
import dagger.Binds
import dagger.Module
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

}
