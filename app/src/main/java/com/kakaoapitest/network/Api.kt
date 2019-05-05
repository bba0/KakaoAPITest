package com.kakaoapitest.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.data.model.BlogModel
import com.data.model.CafeModel
import com.data.model.SearchApiModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api {
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
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            build()
        }
    }


    private fun request(): ApiService = retrofit.create(ApiService::class.java)

    fun searchBlog(query: String, page: Int) : Observable<SearchApiModel<BlogModel>> {
        return request().searchBlog(query = query, page = page)
    }

    fun searchCafe(query: String, page: Int) : Observable<SearchApiModel<CafeModel>> {
        return request().searchCafe(query = query, page = page)
    }

    fun <T> transformerIOMainThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

}