package com.example.swvlmovies.modules.movies.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.swvlmovies.R
import com.example.swvlmovies.modules.movies.features.search.domain.enitiy.Movie
import com.example.swvlmovies.modules.movies.features.search.presentaion.MoviesSearchFragment
import dagger.android.support.DaggerAppCompatActivity

class MoviesActivity : DaggerAppCompatActivity(),MoviesSearchFragment.MovieSelectedListener {
    override fun onMovieSelected(movie: Movie) {
        Toast.makeText(this,movie.title,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_search)
        if(savedInstanceState==null){
            //todo check if tablet add both fragments
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,MoviesSearchFragment.newInstance())
                .commit()
        }
    }

    companion object{
        fun startMe(activity: Activity){
            val intent = Intent(activity, MoviesActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
