package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.model.Document
import com.kakaoapitest.network.Api
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class MainPresenter(override var mView: MainContract.View) : MainContract.Presenter {
    var mSortType = SortType.TITLE
    var mApiType = ApiType.ALL
    var mQuery = ""
    var mDocumentList = ArrayList<Document>()

    override fun search(query: String) {
        search(query, false)
    }

    override fun search(query: String, isMore: Boolean) {
        mQuery = query
        var observableList = ArrayList<Observable<List<Document>>>().apply {
            when(mApiType) {
                ApiType.BLOG -> {
                    add(searchBlog(query))
                }
                ApiType.CAFE -> {
                    add(searchCafe(query))
                }
                else -> {
                    add(searchBlog(query))
                    add(searchCafe(query))
                }
            }
        }
        Observable.zip(observableList) {
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
            .map {
                sortList(it)
            }
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                if (isMore) {
                    mDocumentList.addAll(it)
                    mView.addDocument(it)
                } else {
                    mDocumentList.apply {
                        clear()
                        addAll(it)
                    }
                    mView.setDocument(it)
                }

            }, {
                Log.e("lol", it.message)
            }, {

            })
    }

    private fun sortList(it: List<Document>): List<Document> {
        var sortList = when (mSortType) {
            SortType.DATE -> {
                it.sortedBy {
                    it.date?.time ?: 0
                }
            }
            SortType.TITLE -> {
                it.sortedBy {
                    it.title ?: ""
                }
            }
        }
        return sortList
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

    override fun changeApiType(index: Int) {
        ApiType.values()[index].apply {
            if (mApiType != this) {
                mApiType = this
                search(mQuery)
            }
        }
    }

    override fun changeSortType(index: Int) {
        SortType.values()[index].apply {
            if (mSortType != this) {
                mSortType = this
                sortList(mDocumentList)
                mView.setDocument(mDocumentList)
            }
        }
    }

    enum class SortType {
        TITLE, DATE
    }

    enum class ApiType {
        ALL, BLOG, CAFE
    }

}