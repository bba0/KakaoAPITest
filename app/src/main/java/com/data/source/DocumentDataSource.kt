package com.data.source

import com.data.model.Document
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.Single

interface DocumentDataSource {
    fun getDocuments(apiType: MainPresenter.ApiType, query: String): Single<List<Document>>
    fun moreDocuments(apiType: MainPresenter.ApiType, page: Int, query: String): Single<List<Document>>
    fun getAllCacheDocuments(): List<Document>
}