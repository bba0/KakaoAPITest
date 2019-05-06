package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.data.model.Document
import com.kakaoapitest.data.model.SearchQuery
import com.kakaoapitest.data.source.document.DocumentRepository
import com.kakaoapitest.data.source.searchquery.SearchQueryRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(override var mView: MainContract.View, private var mSearchQueryRepository: SearchQueryRepository, private var mDocumentRepository: DocumentRepository) : MainContract.Presenter {
    private var mSortType = SortType.Title
    private var mApiType = ApiType.All
    private var mQuery = ""
    set(value) {
        if (field != value) {
            mSearchQueryRepository.addSearchQuery(SearchQuery(value))
            getSearchQuerys()
        }
        field = value
    }
    private var mPage = 1
    private var mCompositeDisposable = CompositeDisposable()
    private var isLoading = false

    override fun search(query: String, isMore: Boolean) {
        if (query.isNullOrEmpty()) {
            return
        }

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
            .compose(transformerIOMainThread())
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
            SortType.DateTime -> {
                it.sortedBy {
                    it.date?.time ?: 0
                }
            }
            SortType.Title -> {
                it.sortedBy {
                    it.title ?: ""
                }
            }
        }
    }

    override fun changeApiType(type: String) {
        ApiType.valueOf(type)?.apply {
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
        getSearchQuerys()
    }

    private fun getSearchQuerys() {
        mCompositeDisposable.add(mSearchQueryRepository
            .getSearchQueryList()
            .map {
                it.sortedByDescending { item ->
                    item.time
                }
            }
            .flatMap {
                Observable.fromIterable(it)
            }
            .map {
                it.query
            }
            .toList()
            .toObservable()
            .compose(transformerIOMainThread())
            .subscribe({
                mView.setSearchQuery(it)
            }, {
                Log.e("lol", it.toString())
            }, {

            }))
    }

    private fun <T> transformerIOMainThread(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    enum class SortType {
        Title, DateTime
    }

    enum class ApiType {
        All, Blog, Cafe
    }
}