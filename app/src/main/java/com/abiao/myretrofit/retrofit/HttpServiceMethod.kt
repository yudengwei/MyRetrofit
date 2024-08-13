package com.abiao.myretrofit.retrofit

import okhttp3.ResponseBody
import java.lang.reflect.Method

abstract class HttpServiceMethod<ResponseT, ReturnT>(
    private val requestFactory: RequestFactory,
    private val callFactory: okhttp3.Call.Factory,
    private val responseConverter: Converter<ResponseBody, ResponseT>
) : ServiceMethod<ReturnT>() {

    companion object {
        fun <ResponseT, ReturnT> parseAnnotations(
            retrofit: Retrofit,
            method: Method,
            requestFactory: RequestFactory
        ): HttpServiceMethod<ResponseT, ReturnT>? {

        }
    }

    override fun invoke(proxy: Any, args: Array<Any>?): ReturnT? {
        val call =
        return null
    }

    protected abstract fun adapt(call: Call<ResponseT>, args: Array<Any>) : ReturnT

    class CallAdapted<ResponseT, ReturnT>(
        requestFactory: RequestFactory,
        callFactory: okhttp3.Call.Factory,
        responseConverter: Converter<ResponseBody, ResponseT>,
        private val callAdapter: CallAdapter<ResponseT, ReturnT>
    ) : HttpServiceMethod<ResponseT, ReturnT>(requestFactory, callFactory, responseConverter) {
        override fun adapt(call: Call<ResponseT>, args: Array<Any>): ReturnT {
            TODO("Not yet implemented")
        }

    }
}