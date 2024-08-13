package com.abiao.myretrofit.retrofit

import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout

class OkHttpCall<T>(
    private val requestFactory: RequestFactory,
    private val instance: Any,
    private val args: Array<Any>?,
    private val callFactory: okhttp3.Call.Factory,
    private val responseConverter: Converter<ResponseBody, T>
) : Call<T> {

    override fun execute(): Response<T> {

    }

    override fun isExecuted(): Boolean {
    }

    override fun cancel() {
    }

    override fun isCancel(): Boolean {
    }

    override fun enqueue(callback: Callback<T>) {
    }

    override fun clone(): Call<T> {
        return OkHttpCall(requestFactory, instance, args, callFactory, responseConverter)
    }

    override fun request(): Request {

    }

    override fun timeOut(): Timeout {
    }
}