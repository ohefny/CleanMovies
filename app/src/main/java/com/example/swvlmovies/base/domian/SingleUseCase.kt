package com.example.swvlmovies.base.domian

import io.reactivex.Single


abstract class SingleUseCase<in Params,out Type> where Type : Any {
    abstract fun build(params: Params): Single<out Type>
}