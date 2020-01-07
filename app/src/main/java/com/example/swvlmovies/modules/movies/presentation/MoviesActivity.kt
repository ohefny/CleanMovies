package com.example.swvlmovies.modules.movies.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swvlmovies.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    companion object{
        fun startMe(activity: Activity){
            val intent = Intent(activity, MoviesActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
