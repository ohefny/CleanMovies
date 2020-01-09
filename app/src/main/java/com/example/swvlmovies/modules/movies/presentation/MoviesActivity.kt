package com.example.swvlmovies.modules.movies.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swvlmovies.R
import com.example.swvlmovies.modules.movies.search.presentaion.MoviesSearchFragment
import dagger.android.support.DaggerAppCompatActivity

class MoviesActivity : DaggerAppCompatActivity() {

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
