package com.floriandenu.bookscanning.utils

import com.floriandenu.bookscanning.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("key", BuildConfig.GOOGLE_BOOK_API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}