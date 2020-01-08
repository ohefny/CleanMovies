package com.example.swvlmovies.core.schedulers

import com.example.swvlmovies.base.presentation.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.annotations.NonNull
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.disposables.Disposable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


class TestPostExecutionThread :PostExecutionThread{
    override val scheduler: Scheduler = object : Scheduler() {
        override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
            // this prevents StackOverflowErrors when scheduling with a delay
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() },false)
        }
    }

}
