package com.example.swvlmovies.base.data

import io.reactivex.Scheduler
import org.jetbrains.annotations.Async

interface PostExecutionThread {
    val scheduler: Scheduler
}