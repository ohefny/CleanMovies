package com.example.swvlmovies.core.application

import com.example.swvlmovies.BuildConfig
import com.example.swvlmovies.core.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class SwvlMoviesApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.BUILD_TYPE.equals("debug"))
            Timber.plant(Timber.DebugTree())
    }

}