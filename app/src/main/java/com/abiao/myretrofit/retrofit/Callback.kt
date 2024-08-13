package com.abiao.myretrofit.retrofit

interface Callback<T> {

    fun onResponse(call: Call<T>, response: Response<T>)

    fun onFailure(call: Call<T>, t: Throwable)

}