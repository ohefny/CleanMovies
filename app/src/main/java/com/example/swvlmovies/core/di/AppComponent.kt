package com.example.swvlmovies.core.di
import com.example.swvlmovies.core.application.SwvlMoviesApplication
import com.example.swvlmovies.core.di.modules.*
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
    ExecutorsModule::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<SwvlMoviesApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SwvlMoviesApplication>()

}