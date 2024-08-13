package com.abiao.myretrofit.retrofit

import java.lang.reflect.Method

fun methodError(method: Method, message: String, vararg args: Any, cause: Throwable? = null) : RuntimeException {
    val errorMessage = String.format(message, args)
    return IllegalArgumentException(
        "${errorMessage}\n for method ${method.declaringClass.simpleName}.${method.name}",
        cause
    )
}