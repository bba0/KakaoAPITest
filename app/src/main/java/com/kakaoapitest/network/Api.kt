package com.kakaoapitest.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Api {
    private val HOST = "http://dapi.kakao.com"
    private val KAKAO_API_KEY = "KakaoAK 0904ee87d5006949d5c8c5aeeb303038"
    val gson: Gson by lazy {
        GsonBuilder().run {
            setLenient()
            create()
        }
    }
    val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder().run {
            addInterceptor {
                var originalRequest = it.request()
                var request = originalRequest.newBuilder().run {
                    addHeader("Authorization", KAKAO_API_KEY)
                    build()
                }
                it.proceed(request)
            }
            build()
        }
        Retrofit.Builder().run {
            baseUrl(HOST)
            client(client)
            build()
        }
    }


}