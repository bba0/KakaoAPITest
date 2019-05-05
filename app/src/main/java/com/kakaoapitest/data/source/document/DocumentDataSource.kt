package com.kakaoapitest.data.source.document

import com.kakaoapitest.data.model.Document
import com.kakaoapitest.ui.main.MainPresenter
import io.reactivex.Single

interface DocumentDataSource {
    fun getDocuments(apiType: MainPresenter.ApiType, query: String): Single<List<Document>>
    fun moreDocuments(apiType: MainPresenter.ApiType, page: Int, query: String): Single<List<Document>>
}