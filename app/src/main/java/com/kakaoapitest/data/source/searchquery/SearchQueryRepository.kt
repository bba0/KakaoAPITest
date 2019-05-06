package com.kakaoapitest.data.source.searchquery

import com.kakaoapitest.data.model.SearchQuery
import io.reactivex.Observable

class SearchQueryRepository private constructor(private val localSearchQueryDataSource: SearchQueryDataSource) : SearchQueryDataSource {
    private var  cacheMap = HashMap<String, SearchQuery>()
    override fun addSearchQuery(searchQuery: SearchQuery) {
        cacheMap[searchQuery.query] = searchQuery
        localSearchQueryDataSource.addSearchQuery(searchQuery)
    }

    override fun getSearchQueryList(): Observable<List<SearchQuery>> {
        return if (cacheMap.size == 0) {
            localSearchQueryDataSource.getSearchQueryList()
                .filter {
                    it.isNotEmpty()
                }
                .flatMap {
                    Observable.fromIterable(it)
                }
                .doOnNext {
                    cacheMap[it.query] = it
                }
                .toList()
                .toObservable()
        } else {
            Observable.just(cacheMap)
                .map {
                    it.values
                }
                .map {
                    ArrayList<SearchQuery>(it)
                }
        }
    }

    companion object {
        private var INSTANCE: SearchQueryRepository? = null
        @JvmStatic
        fun getInstance(localSearchQueryDataSource: SearchQueryDataSource): SearchQueryRepository {
            if (INSTANCE == null) {
                synchronized(LocalSearchQueryDataSource::class.java) {
                    INSTANCE = SearchQueryRepository(localSearchQueryDataSource)
                }
            }
            return INSTANCE!!
        }
    }
}