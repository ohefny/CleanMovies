package com.example.swvlmovies.core.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.swvlmovies.base.presentation.PostExecutionThread
import com.example.swvlmovies.base.domian.ThreadExecutor
import com.example.swvlmovies.core.data.Resource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.net.SocketException

fun <T> Flowable<T>.flatMapToResource(): Flowable<Resource<T>> {
    return map { Resource.Success(it) as Resource<T> }
        .startWith(Resource.Loading())
        .onErrorReturn { Resource.Failure(it) }
}

fun <T> Single<T>.flatMapToResource(): Flowable<Resource<T>> {
    return toFlowable().flatMapToResource()
}
fun <T> Single<T>.applyAsyncSchedulers(executor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread): Single<T> =
    this.compose {
        it.subscribeOn(Schedulers.from(executor)).observeOn(postExecutionThread.scheduler)
    }

fun <T> Flowable<T>.applyAsyncSchedulers(executor: ThreadExecutor, postExecutionThread: PostExecutionThread): Flowable<T> =
    this.compose {
        it.subscribeOn(Schedulers.from(executor)).observeOn(postExecutionThread.scheduler)
    }

fun Completable.applyAsyncSchedulers(executor: ThreadExecutor,
                                     postExecutionThread: PostExecutionThread): Completable =
    this.compose {
        it.subscribeOn(Schedulers.from(executor)).observeOn(postExecutionThread.scheduler)
    }

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun <T> Flowable<Resource<T>>.publishResource(
    liveData: MutableLiveData<Resource<T>>,
    postExecutionThread: PostExecutionThread,
    threadExecutor: ThreadExecutor
): Disposable {
    return applyAsyncSchedulers(executor =threadExecutor,postExecutionThread = postExecutionThread)
        .doOnError { Timber.e(it) }
        .subscribe { liveData.postValue(it) }
}

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    observe(lifecycleOwner, Observer(action))
}

fun <T> LiveData<Resource<T>>.observeResource(
    lifecycleOwner: LifecycleOwner, doOnSuccess: T.() -> Unit,
    doOnError: ((Throwable) -> Unit)? = null,
    doOnNetworkError: ((Throwable) -> Unit)? = null,
    doOnTerminate: (() -> Unit)? = null,
    doOnLoading: (() -> Unit)? = null
) {
    observe(lifecycleOwner) { res ->
        when (res) {
            is Resource.Success -> doOnSuccess.invoke(res.data).also { doOnTerminate?.invoke() }
            is Resource.Loading -> doOnLoading?.invoke()
            is Resource.Failure -> {
                if (res.throwable.isNetworkError())
                    doOnNetworkError?.invoke(res.throwable) ?: doOnError?.invoke(res.throwable)
                else
                    doOnError?.invoke(res.throwable)
                doOnTerminate?.invoke()
            }
        }
    }
}

fun Throwable.isNetworkError() = this is SocketException