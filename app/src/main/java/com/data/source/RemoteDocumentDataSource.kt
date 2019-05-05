package com.data.source

import com.data.model.Document
import com.kakaoapitest.network.Api
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import kotlin.collections.ArrayList

object RemoteDocumentDataSource : DocumentDataSource {
    override fun getDocuments(apiType: MainPresenter.ApiType, query: String): Single<List<Document>> = moreDocuments(apiType, 1, query)

    override fun moreDocuments(apiType: MainPresenter.ApiType, page: Int, query: String): Single<List<Document>> {
        var observableList = ArrayList<Observable<List<Document>>>().apply {
            when(apiType) {
                MainPresenter.ApiType.BLOG -> {
                    add(searchBlog(query))
                }
                MainPresenter.ApiType.CAFE -> {
                    add(searchCafe(query))
                }
                else -> {
                    add(searchBlog(query))
                    add(searchCafe(query))
                }
            }
        }
        return Observable.zip(observableList) {
            it
        }.map {
            Arrays.asList(*it)
        }.flatMap {
            Observable.fromIterable(it)
        }
            .map {
                it as ArrayList<Document>
            }
            .flatMap {
                Observable.fromIterable(it)
            }
            .toList()
    }

    private fun searchBlog(query: String): Observable<List<Document>> {
        return Api.searchBlog(query)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
    }


    private fun searchCafe(query: String): Observable<List<Document>> {
        return Api.searchCafe(query)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
    }

}