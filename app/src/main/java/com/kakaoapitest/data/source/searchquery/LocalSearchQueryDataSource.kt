package com.kakaoapitest.data.source.searchquery

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kakaoapitest.data.model.SearchQuery
import io.reactivex.Observable

class LocalSearchQueryDataSource private constructor(private val context: Context) : SearchQueryDataSource {
    private val NAME = "SearchQuery"
    private val KEY = "key"
    private val sharedPreference by lazy {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }
    private val gson by lazy {
        Gson()
    }
    override fun getSearchQueryList(): Observable<List<SearchQuery>> {
        return Observable.create<String> {
            it.onNext(sharedPreference.getString(KEY, ""))
            it.onComplete()
        }.map {
            return@map if (it.isNullOrEmpty()) {
                ArrayList<SearchQuery>()
            } else {
                gson.fromJson(it, TypeToken.getParameterized(List::class.java, SearchQuery::class.java).type)
            }
        }
    }

    override fun addSearchQuery(searchQuery: SearchQuery) {
        var data = sharedPreference.getString(KEY, "")
        var saveData = ArrayList<SearchQuery>()
        if (!data.isNullOrEmpty()) {
            saveData = gson.fromJson(data, TypeToken.getParameterized(List::class.java, SearchQuery::class.java).type)
        }
        if (saveData.contains(searchQuery)) {
            saveData.remove(searchQuery)
        }
        saveData.add(searchQuery)
        sharedPreference.edit().run {
            putString(KEY, gson.toJson(saveData))
            commit()
        }
    }

    companion object {
        private var INSTANCE: LocalSearchQueryDataSource? = null
        @JvmStatic
        fun getInstance(context: Context): LocalSearchQueryDataSource {
            if (INSTANCE == null) {
                synchronized(LocalSearchQueryDataSource::class.java) {
                    INSTANCE = LocalSearchQueryDataSource(context)
                }
            }
            return INSTANCE!!
        }
    }
}