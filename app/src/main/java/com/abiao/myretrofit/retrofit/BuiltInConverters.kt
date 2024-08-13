package com.abiao.myretrofit.retrofit

class BuiltInConverters : Converter.Factory() {

    class ToStringConverter : Converter<Any, String> {

        companion object {
            val INSTANCE: ToStringConverter = ToStringConverter()
        }

        override fun convert(value: Any?): String? {
            return value?.toString()
        }

    }
}