package com.abiao.myretrofit.retrofit

abstract class ParameterHandler<T> {

    abstract fun apply(requestBuilder: RequestBuilder, value: T?)

    class Path<T>(
        private val name: String,
        private val valueConverter: Converter<T, String>,
        private val encoded: Boolean
    ) : ParameterHandler<T>() {

        override fun apply(requestBuilder: RequestBuilder, value: T?) {
            requestBuilder.addPathParam(name, valueConverter.convert(value), encoded)
        }

    }
}

