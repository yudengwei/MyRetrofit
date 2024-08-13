package com.abiao.myretrofit.retrofit

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.lang.reflect.Type

@Suppress("UNCHECKED_CAST")
class Retrofit(
    val converterFactories: List<Converter.Factory>
) {

    private val serviceMethodCache = HashMap<Method, Any>()


    fun <T> create(service: Class<T>) : T? {
        if (!validateServiceInterface(service)) {
            return null
        }
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf(service),
            object : InvocationHandler {
                private val emptyArgs = emptyArray<Any>()

                override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any? {
                    if (method.declaringClass == Any::class.java) {
                        val a = args ?: emptyArgs
                        return method.invoke(this, a)
                    }
                    return loadServiceMethod(service, method).invoke(proxy, args)
                }
            }
        ) as? T
    }

    fun validateServiceInterface(service: Class<*>) : Boolean {
        if (!service.isInterface) {
            return false
        }
        if (service.typeParameters.isNotEmpty()) {
            return false
        }
        return service.interfaces.all { it.typeParameters.isEmpty() }
    }

    fun loadServiceMethod(service: Class<*>, method: Method) : ServiceMethod<*> {
        val lookup = serviceMethodCache[method]
        return if (lookup is ServiceMethod<*>) lookup else {
            val result = ServiceMethod.parseAnnotations<Any>(this, service, method)
            serviceMethodCache[method] = result
            result
        }
    }

    fun <T> stringConverter(type: Type, annotations: Array<Annotation>?) : Converter<T, String> {
        converterFactories.forEach { factory ->
            val convert = factory.stringConverter(type, annotations!!, this@Retrofit)
            if (convert != null) {
                return convert as Converter<T, String>
            }
        }
        return BuiltInConverters.ToStringConverter.INSTANCE as Converter<T, String>
    }
}