package com.kakaoapitest.data.source.searchquery

import com.kakaoapitest.data.model.SearchQuery
import io.reactivex.Observable

interface SearchQueryDataSource {
    fun getSearchQueryList(): Observable<List<SearchQuery>>
    fun addSearchQuery(searchQuery: SearchQuery)
}