package com.abiao.myretrofit

import com.abiao.myretrofit.retrofit.Retrofit
import org.junit.Test

import org.junit.Assert.*
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val retrofit = Retrofit()
        val testApi = retrofit.create(TestApi::class.java)
        val result = testApi?.getUser("阿彪")
        assertNull(result)
    }
}