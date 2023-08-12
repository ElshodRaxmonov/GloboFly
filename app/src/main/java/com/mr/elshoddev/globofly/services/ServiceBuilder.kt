package com.mr.elshoddev.globofly.services

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

object ServiceBuilder {
    private const val URL = "http://10.0.2.2:9000/"

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //Custom interceptor

    private val headerInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder().addHeader("Type-Phone", Build.DEVICE)
                .addHeader("Accept-Langugage", Locale.getDefault().language).build()
            val response = chain.proceed(request)
            return response
        }
    }


    private val client = OkHttpClient.Builder().addInterceptor(headerInterceptor).addInterceptor(logger)
    private val builder =
        Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(
            client.build()
        )

    // instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}