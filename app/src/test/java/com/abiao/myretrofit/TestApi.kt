package com.abiao.myretrofit

import com.abiao.myretrofit.retrofit.annotation.DELETE
import com.abiao.myretrofit.retrofit.annotation.Path

interface TestApi {

    @DELETE("user/")
    fun getUser(@Path("path") userPath: String) : User?
}