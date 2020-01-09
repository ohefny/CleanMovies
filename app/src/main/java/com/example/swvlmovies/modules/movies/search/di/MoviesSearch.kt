package com.example.swvlmovies.modules.movies.search.di

import androidx.lifecycle.ViewModel
import com.example.swvlmovies.core.data.SwvlMoviesDB
import com.example.swvlmovies.core.di.modules.ActivityScope
import com.example.swvlmovies.core.di.modules.ViewModelKey
import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.movies.search.data.MoviesSearchRepositoryImpl
import com.example.swvlmovies.modules.movies.search.domain.MoviesSearchRepository
import com.example.swvlmovies.modules.movies.search.presentaion.MoviesSearchFragment
import com.example.swvlmovies.modules.movies.search.presentaion.MoviesSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap


@Module
abstract class MoviesSearchFragmentBuilder {
    @ContributesAndroidInjector(modules = [MoviesSearchModule::class])
    internal abstract fun contributeMoviesSearchFragment(): MoviesSearchFragment
}

@Module
abstract class MoviesSearchModule {
    @Binds
    abstract fun provideMoviesSearchRepository(impl: MoviesSearchRepositoryImpl): MoviesSearchRepository
    @Binds
    @IntoMap
    @ViewModelKey(MoviesSearchViewModel::class)
    internal abstract fun provideMoviesSearchViewModel(viewModel: MoviesSearchViewModel): ViewModel
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMoviesDAO(db: SwvlMoviesDB): MoviesDAO = db.moviesDao()
    }

}
