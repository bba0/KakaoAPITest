package com.kakaoapitest.data.source.document

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.data.source.searchquery.LocalSearchQueryDataSource
import com.kakaoapitest.data.source.searchquery.SearchQueryDataSource
import com.kakaoapitest.data.source.searchquery.SearchQueryRepository
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.Observable
import io.reactivex.Single

class DocumentRepository private constructor(): DocumentDataSource {
    fun getAllCacheDocuments(): List<Document> = ArrayList(cacheMap.values)

    var remoteDocumentDataSource = RemoteDocumentDataSource
    var  cacheMap = HashMap<String, Document>()
    override fun getDocuments(apiType: MainPresenter.ApiType, query: String): Single<List<Document>> =
        moreDocuments(apiType, 0, query)

    override fun moreDocuments(apiType: MainPresenter.ApiType, page: Int, query: String): Single<List<Document>> {
        return RemoteDocumentDataSource.moreDocuments(apiType, page, query)
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .doOnNext {
                if (cacheMap.containsKey(it.url)) {
                    it.isOpen = cacheMap[it.url]?.isOpen ?: false
                }
                cacheMap[it.url ?: ""] = it
            }
            .toList()
    }

    fun getDocument(url: String): Document? = cacheMap[url]

    fun openDocument(url: String) {
        cacheMap[url]?.isOpen = true
    }

    companion object {
        private var INSTANCE: DocumentRepository? = null
        @JvmStatic
        fun getInstance(): DocumentRepository {
            if (INSTANCE == null) {
                synchronized(LocalSearchQueryDataSource::class.java) {
                    INSTANCE = DocumentRepository()
                }
            }
            return INSTANCE!!
        }
    }
}