package com.kakaoapitest.network

import com.kakaoapitest.model.SearchApiModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/v2/search/blog")
    fun searchBlog(@Path("query") query: String, @Path("page")page: Int = 1, @Path("size")size: Int = 25) :  Observable<SearchApiModel>

    @GET("/v2/search/cafe")
    fun searchCafe(@Path("query") query: String, @Path("page")page: Int = 1, @Path("size")size: Int = 25) :  Observable<SearchApiModel>
}