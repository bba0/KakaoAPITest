package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.data.model.Document
import com.kakaoapitest.data.source.DocumentRepository
import com.kakaoapitest.network.Api
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(override var mView: MainContract.View) : MainContract.Presenter {
    private var mSortType = SortType.TITLE
    private var mApiType = ApiType.ALL
    private var mQuery = ""
    private var mDocumentRepository = DocumentRepository
    private var mPage = 1
    private var mCompositeDisposable = CompositeDisposable()
    private var isLoading = false

    override fun search(query: String, isMore: Boolean) {
        if (isMore) {
            mPage++
        } else {
            mPage = 1
        }
        mQuery = query
        mCompositeDisposable.add(mDocumentRepository.moreDocuments(mApiType, mPage, mQuery)
            .map {
                sortList(it)
            }
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                if (isMore) {
                    mView.addDocument(it)
                    isLoading = false
                } else {
                    mView.setDocument(it)
                }

            }, {
                Log.e("lol", it.message)
            }, {

            }))
    }

    override fun moreLoad() {
        if (!isLoading) {
            isLoading = true
            search(mQuery, true)
        }
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

    override fun pause() {
        mCompositeDisposable.clear()
    }

    override fun resume() {
        mView.setDocument(sortList(mDocumentRepository.getAllCacheDocuments()))
    }

    enum class SortType {
        TITLE, DATE
    }

    enum class ApiType {
        ALL, BLOG, CAFE
    }

}