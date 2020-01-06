package com.example.swvlmovies.base.presentation

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}