package com.example.swvlmovies.core.di.modules

import android.content.Context
import com.example.swvlmovies.BuildConfig
import com.example.swvlmovies.base.data.PostExecutionThread
import com.example.swvlmovies.base.data.ThreadExecutor
import com.example.swvlmovies.core.application.SwvlMoviesApplication
import com.example.swvlmovies.core.data.JobExecutor
import com.example.swvlmovies.core.presentation.UIThread
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: SwvlMoviesApplication): Context = application.applicationContext

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { logMessage -> Timber.e(logMessage) }
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return loggingInterceptor
    }
    @Provides
    fun retrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl("https://google.com").addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client).build()
    }


    @Provides
    @Singleton
    internal fun provideExecutor(threadExecutor: JobExecutor): ThreadExecutor = threadExecutor

    @Provides
    @Singleton
    internal fun provideUIThread(uiThread: UIThread): PostExecutionThread = uiThread
}
