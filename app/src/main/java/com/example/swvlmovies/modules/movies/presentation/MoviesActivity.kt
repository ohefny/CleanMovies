package com.example.swvlmovies.modules.movies.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swvlmovies.R

class MoviesSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_search)
    }


    companion object{
        fun startMe(activity: Activity){
            val intent = Intent(activity, MoviesSearchActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
