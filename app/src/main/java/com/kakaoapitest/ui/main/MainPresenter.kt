package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.model.Document
import com.kakaoapitest.network.Api
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

class MainPresenter(override var mVie: MainContract.View) : MainContract.Presenter {
    override fun search(query: String) {
        var observableList = ArrayList<Observable<List<Document>>>()
        observableList.add(searchBlog(query))
        observableList.add(searchCafe(query))
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
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                Log.e("lol", it.toString())
            }, {
                Log.e("lol", it.message)
            }, {

            })
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

}