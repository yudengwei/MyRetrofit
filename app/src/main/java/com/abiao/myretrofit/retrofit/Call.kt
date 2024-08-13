package com.abiao.myretrofit.retrofit

import okhttp3.Request
import okio.Timeout

interface Call<T> : Cloneable {

    fun execute(): Response<T>

    fun enqueue(callback: Callback<T>)

    fun isExecuted() : Boolean

    fun cancel()

    fun isCancel() : Boolean

    override fun clone() : Call<T>

    fun request(): Request

    fun timeOut() : Timeout
}