package com.example.swvlmovies.modules.splash.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.swvlmovies.core.extention.observe
import com.example.swvlmovies.modules.movies.presentation.MoviesSearchActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class SplashActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory
    private val mViewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this, mViewModelFactory)[SplashViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.isDataReady.observe(this,onEmission=::navigateToMoviesScreen)
    }

    private fun navigateToMoviesScreen() {
        MoviesSearchActivity.startMe(this)
        finish()
    }

}
