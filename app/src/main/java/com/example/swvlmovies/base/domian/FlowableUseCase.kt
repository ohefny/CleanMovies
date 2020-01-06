package com.example.swvlmovies.base.domian

import io.reactivex.Flowable

abstract class FlowableUseCase <in Params, out Type> where Type : Any {
    abstract fun build(params: Params): Flowable<out Type>
}