package com.kakaoapitest.data.source.document

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.network.Api
import com.kakaoapitest.network.ApiService
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

class RemoteDocumentDataSource private constructor(var api: ApiService) : DocumentDataSource {

    override fun getDocuments(apiType: MainPresenter.ApiType, query: String): Single<List<Document>> =
        moreDocuments(apiType, 1, query)

    override fun moreDocuments(apiType: MainPresenter.ApiType, page: Int, query: String): Single<List<Document>> {
        var observableList = ArrayList<Observable<List<Document>>>().apply {
            when (apiType) {
                MainPresenter.ApiType.Blog -> {
                    add(searchBlog(query, page))
                }
                MainPresenter.ApiType.Cafe -> {
                    add(searchCafe(query, page))
                }
                else -> {
                    add(searchBlog(query, page))
                    add(searchCafe(query, page))
                }
            }
        }
        return Observable.zip(observableList) {
            it
        }.map {
            Arrays.asList(*it)
        }.flatMap {
            Observable.fromIterable(it)
        }.map {
            it as ArrayList<Document>
        }.flatMap {
            Observable.fromIterable(it)
        }.toList()
    }

    private fun searchBlog(query: String, page: Int): Observable<List<Document>> {
        return Api.searchBlog(query, page, 25)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
    }


    private fun searchCafe(query: String, page: Int): Observable<List<Document>> {
        return Api.searchCafe(query, page, 25)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
    }

    companion object {
        private var INSTANCE: RemoteDocumentDataSource? = null
        @JvmStatic
        fun getInstance(api: ApiService): RemoteDocumentDataSource {
            if (INSTANCE == null) {
                synchronized(RemoteDocumentDataSource::class.java) {
                    INSTANCE = RemoteDocumentDataSource(api)
                }
            }
            return INSTANCE!!
        }
    }

}