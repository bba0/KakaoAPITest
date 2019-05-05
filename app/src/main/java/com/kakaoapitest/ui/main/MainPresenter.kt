package com.kakaoapitest.ui.main

import android.util.Log
import com.data.model.Document
import com.data.source.DocumentRepository
import com.kakaoapitest.network.Api

class MainPresenter(override var mView: MainContract.View) : MainContract.Presenter {
    private var mSortType = SortType.TITLE
    private var mApiType = ApiType.ALL
    private var mQuery = ""
    private var mDocumentRepository = DocumentRepository
    private var mPage = 1

    override fun search(query: String, isMore: Boolean) {
        if (isMore) {
            mPage++
        } else {
            mPage = 1
        }
        mQuery = query
        mDocumentRepository.moreDocuments(mApiType, mPage, mQuery)
            .map {
                sortList(it)
            }
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                if (isMore) {
                    mView.addDocument(it)
                } else {
                    mView.setDocument(it)
                }

            }, {
                Log.e("lol", it.message)
            }, {

            })
    }

    private fun sortList(it: List<Document>): List<Document> {
        return when (mSortType) {
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
    }

    override fun changeApiType(index: Int) {
        ApiType.values()[index].apply {
            if (mApiType != this) {
                mApiType = this
                search(mQuery, false)
            }
        }
    }

    override fun changeSortType(index: Int) {
        SortType.values()[index].apply {
            if (mSortType != this) {
                mSortType = this
                mView.setDocument(sortList(mDocumentRepository.getAllCacheDocuments()))
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