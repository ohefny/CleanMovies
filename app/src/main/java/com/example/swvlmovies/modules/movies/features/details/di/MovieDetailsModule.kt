package com.example.swvlmovies.modules.movies.features.details.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Scope
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.core.data.SwvlMoviesDB
import com.example.swvlmovies.core.di.modules.ViewModelKey
import com.example.swvlmovies.modules.common.data.local.MoviesDAO
import com.example.swvlmovies.modules.movies.features.details.data.MovieDetailsRepositoryImpl
import com.example.swvlmovies.modules.movies.features.details.data.source.local.PhotosDAO
import com.example.swvlmovies.modules.movies.features.details.data.source.remote.MoviePhotosAPI
import com.example.swvlmovies.modules.movies.features.details.domain.MovieDetailsRepository
import com.example.swvlmovies.modules.movies.features.details.presentation.MovieDetailsFragment
import com.example.swvlmovies.modules.movies.features.details.presentation.MovieDetailsViewModel
import retrofit2.Retrofit


@Module
abstract class MovieDetailsFragmentBuilder {
    @ContributesAndroidInjector(modules = [MovieDetailsModuleModule::class])
    internal abstract fun contributeMovieDetailsFragment(): MovieDetailsFragment
}

@Module
abstract class MovieDetailsModuleModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    internal abstract fun provideMovieDetailsModuleViewModel(viewModel: MovieDetailsViewModel): ViewModel
    @Binds
    internal abstract fun provideMovieDetailsRepository(impl: MovieDetailsRepositoryImpl): MovieDetailsRepository
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideMoviesDAO(db: SwvlMoviesDB): MoviesDAO = db.moviesDao()
        @Provides
        @JvmStatic
        fun providePhotosDAO(db: SwvlMoviesDB): PhotosDAO = db.photosDao()
        @Provides
        @JvmStatic
        fun provideRetrofit(retrofit: Retrofit): MoviePhotosAPI = retrofit.create(MoviePhotosAPI::class.java)
    }

}
