package com.abiao.myretrofit.retrofit

import java.lang.reflect.Type

interface Converter<F, T> {

    fun convert(value: F?) : T?

    abstract class Factory {

        fun stringConverter(
            type: Type, annotations: Array<Annotation>, retrofit: Retrofit
        ) : Converter<*, String>? {
            return null
        }
    }
}