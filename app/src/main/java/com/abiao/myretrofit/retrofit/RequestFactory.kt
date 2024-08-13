package com.abiao.myretrofit.retrofit

import com.abiao.myretrofit.retrofit.annotation.DELETE
import com.abiao.myretrofit.retrofit.annotation.Path
import java.lang.reflect.Method
import java.lang.reflect.Type
import java.util.regex.Pattern

class RequestFactory(
    builder: Builder
) {
    private val service: Class<*> = builder.service
    private val method: Method = builder.method
    val httpMethod: String? = builder.httpMethod
    private val relativeUrl: String? = builder.relativeUrl
    private val hasBody = builder.hasBody
    private val parameterHandlers: Array<ParameterHandler<*>?>? = builder.parameterHandlers
    val isKotlinSuspendFunction: Boolean = false

    companion object {
        fun parseAnnotations(retrofit: Retrofit, service: Class<*>, method: Method) : RequestFactory {
            return Builder(retrofit, service, method).build()
        }
    }

    fun create(instance: Any?, args: Array<Any>) : okhttp3.Request {

    }

    class Builder(val retrofit: Retrofit, val service: Class<*>, val method: Method) {

        private val methodAnnotations: Array<Annotation> = method.annotations
        private val parameterAnnotationsArray: Array<Array<Annotation>> = method.parameterAnnotations
        private val parameterTypes: Array<Type> = method.genericParameterTypes
         var relativeUrl: String? = null
        private var relativeUrlParamNames: Set<String>? = null
        var parameterHandlers: Array<ParameterHandler<*>?>? = null

        var httpMethod: String? = null
        var hasBody: Boolean = false

        private val PARAM: String = "[a-zA-Z][a-zA-Z0-9_-]*"

        private val PARAM_URL_REGEX: Pattern =
            Pattern.compile("\\{($PARAM)\\}")
        private val PARAM_NAME_REGEX: Pattern = Pattern.compile(PARAM)
        private var gotPath = false

        fun build() : RequestFactory {
            methodAnnotations.forEach {
                parseMethodAnnotation(it)
            }
            val parameterCount = parameterAnnotationsArray.size
            println("parameterCount: $parameterCount")
            parameterHandlers = arrayOfNulls(parameterCount)
            for (p in 0 until parameterCount) {
                parameterHandlers!![p] = parseParameter(
                    p, parameterTypes[p], parameterAnnotationsArray[p], p == parameterCount - 1
                )
            }
            return RequestFactory(this)
        }

        private fun parseParameter(
            p: Int, parameterType: Type, annotations: Array<Annotation>?, allowContinuation: Boolean
        ) : ParameterHandler<*>? {
            var result: ParameterHandler<*>? = null
            annotations?.forEach {
                val annotationAction = parseParameterAnnotation(
                    p, parameterType, annotations, it
                )
                if (annotationAction != null) {
                    result = annotationAction
                }
            }
            return result
        }

        private fun parseParameterAnnotation(
            p: Int, parameterType: Type, annotations: Array<Annotation>?, annotation: Annotation
        ) : ParameterHandler<*>? {
            if (annotation is Path) {
                gotPath = true
                val name = annotation.value
                val covert = retrofit.stringConverter<Any>(type = parameterType, annotations)
                return ParameterHandler.Path(name, covert, annotation.encoded)
            }
            return null
        }

        private fun parseMethodAnnotation(annotation: Annotation) {
            if (annotation is DELETE) {
                parseHttpMethodAndPath("DELETE", annotation.value)
            }
        }

        private fun parseHttpMethodAndPath(httpMethod: String, value: String, hasBody: Boolean = false) {
            if (this.httpMethod != null) {
                throw methodError(
                    method = method,
                    message = "Only one HTTP method is allow. Found: %s and %s.",
                    this.httpMethod!!, httpMethod
                )
            }
            this.httpMethod = httpMethod
            this.hasBody = hasBody
            if (value.isEmpty()) {
                return
            }

            relativeUrl = value
            relativeUrlParamNames = parsePathParameters(value)
        }

        fun parsePathParameters(path: String) : Set<String> {
            val m = PARAM_URL_REGEX.matcher(path)
            val patterns = LinkedHashSet<String>()
            while(m.find()) {
                patterns.add(m.group(1)!!)
            }
            return patterns
        }
    }
}