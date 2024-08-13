package com.abiao.myretrofit.retrofit

import java.lang.reflect.Type

interface CallAdapter<R, T> {

    fun responseType() : Type

    fun adapt(call: Call<R>) : T

    abstract class Factory {
        abstract fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ) : CallAdapter<*, *>


    }
}