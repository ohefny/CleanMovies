package com.example.swvlmovies.core.di
import com.example.swvlmovies.core.application.SwvlMoviesApplication
import com.example.swvlmovies.core.di.modules.ActivityBuilder
import com.example.swvlmovies.core.di.modules.AppModule
import com.example.swvlmovies.core.di.modules.ExecutorsModule
import com.example.swvlmovies.core.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ActivityBuilder::class,
    ViewModelModule::class,
    ExecutorsModule::class
])
interface AppComponent : AndroidInjector<SwvlMoviesApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SwvlMoviesApplication>()

}