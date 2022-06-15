package com.reserach.newsapp.api

import android.content.Context
import com.reserach.newsapp.util.Constant
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val token = Constant.API_KEY
        proceed(
            request().newBuilder()
                .addHeader("Authorization", token)
                .build()
        )
    }
}