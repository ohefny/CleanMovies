package com.example.swvlmovies.modules.splash.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.swvlmovies.base.domian.ThreadExecutor
import com.example.swvlmovies.base.presentation.PostExecutionThread
import com.example.swvlmovies.core.extention.addTo
import com.example.swvlmovies.core.extention.applyAsyncSchedulers
import com.example.swvlmovies.modules.splash.domain.PrepareMoviesData
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashViewModel @Inject constructor(prepareMoviesData: PrepareMoviesData,threadExecutor: ThreadExecutor,postExecutionThread: PostExecutionThread) : ViewModel() {
    private val _isDataReady = MutableLiveData<Unit>()
    val isDataReady:LiveData<Unit> = _isDataReady
    private val compositeDisposable=CompositeDisposable()
    init {
        Completable.timer(1000,TimeUnit.MILLISECONDS)
            .mergeWith(prepareMoviesData.build(Unit))
            .applyAsyncSchedulers(executor =threadExecutor,postExecutionThread = postExecutionThread)
            .subscribe { _isDataReady.postValue(Unit)}
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}