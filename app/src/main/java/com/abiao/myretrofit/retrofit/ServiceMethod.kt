package com.abiao.myretrofit.retrofit

import java.lang.reflect.Method

abstract class ServiceMethod<T> {

    companion object {

        fun <T> parseAnnotations(retrofit: Retrofit, service: Class<*>, method: Method) : ServiceMethod<T> {
            val requestFactory = RequestFactory.parseAnnotations(retrofit, service, method)
            return HttpServiceMethod()
        }
    }

    abstract fun invoke(proxy: Any, args: Array<Any>?) : T?
}