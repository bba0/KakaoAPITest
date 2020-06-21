package com.kakaoapitest.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kakaoapitest.data.model.BlogModel
import com.kakaoapitest.data.model.CafeModel
import com.kakaoapitest.data.model.SearchApiModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api : ApiService {

    private const val HOST = "https://dapi.kakao.com"
    private const val KAKAO_API_KEY = "KakaoAK 0904ee87d5006949d5c8c5aeeb303038"
    private const val GSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS+HH:mm"
    val gson: Gson by lazy {
        GsonBuilder().run {
            setLenient()
            setDateFormat(GSON_DATE_FORMAT)
            create()
        }
    }
    private val retrofit: Retrofit by lazy {
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
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            build()
        }
    }

    private val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override fun searchBlog(query: String, page: Int, size: Int): Observable<SearchApiModel<BlogModel>> {
        return apiService.searchBlog(query = query, page = page)
    }

    override fun searchCafe(query: String, page: Int, size: Int): Observable<SearchApiModel<CafeModel>> {
        return apiService.searchCafe(query = query, page = page)
    }

}