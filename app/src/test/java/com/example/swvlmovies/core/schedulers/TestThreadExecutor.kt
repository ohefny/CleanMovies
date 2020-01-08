package com.example.swvlmovies.core.schedulers

import com.example.swvlmovies.base.domian.ThreadExecutor

class TestThreadExecutor() : ThreadExecutor {
    override fun execute(command: Runnable) {
        command.run()
    }
}