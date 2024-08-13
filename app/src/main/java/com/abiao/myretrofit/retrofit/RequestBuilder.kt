package com.abiao.myretrofit.retrofit

class RequestBuilder {

    fun addPathParam(name: String, value: String?, encoded: Boolean) {
        println("name: $name, value: $value, encode: $encoded")
    }

    private fun canonicalizeForPath(input: String, alreadyEncoded: Boolean) {
        var codePoint = 0
        for (i in input.indices step Character.charCount(codePoint)) {
            codePoint = input.codePointAt(i)

        }
    }
}