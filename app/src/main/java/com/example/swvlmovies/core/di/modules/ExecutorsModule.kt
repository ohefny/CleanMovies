package com.example.swvlmovies.core.di.modules

import com.example.swvlmovies.base.data.PostExecutionThread
import com.example.swvlmovies.base.data.ThreadExecutor
import com.example.swvlmovies.core.data.JobExecutor
import com.example.swvlmovies.core.presentation.UIThread
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ExecutorsModule {
    @Binds
    @Singleton
    internal abstract fun provideExecutor(threadExecutor: JobExecutor): ThreadExecutor

    @Binds
    @Singleton
    internal abstract fun provideUIThread(uiThread: UIThread): PostExecutionThread

}