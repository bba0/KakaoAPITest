package com.kakaoapitest.network

import com.kakaoapitest.model.SearchApiModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/v2/search/blog")
    fun searchBlog(@Query("query") query: String, @Query("page")page: Int = 1, @Query("size")size: Int = 25) :  Observable<SearchApiModel>

    @GET("/v2/search/cafe")
    fun searchCafe(@Query("query") query: String, @Query("page")page: Int = 1, @Query("size")size: Int = 25) :  Observable<SearchApiModel>
}