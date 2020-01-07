package com.example.swvlmovies.core.di.modules
import com.example.swvlmovies.modules.splash.di.SplashModule
import com.example.swvlmovies.modules.splash.presentation.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    internal abstract fun contributeSearchActivity(): SplashActivity


}

