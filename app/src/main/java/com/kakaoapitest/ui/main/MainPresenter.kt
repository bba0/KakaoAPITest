package com.kakaoapitest.ui.main

import android.util.Log
import com.kakaoapitest.model.Document
import com.kakaoapitest.network.Api
import io.reactivex.Observable

class MainPresenter(override var mVie: MainContract.View) : MainContract.Presenter {
    override fun search(query: String) {
        searchBlog(query)
        searchCafe(query)
    }

    fun searchBlog(query: String) {
        Api.searchBlog(query)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                Log.e("lol", it.toString())
            }, {
                Log.e("lol", it.toString())
            }, {

            })
    }


    fun searchCafe(query: String) {
        Api.searchCafe(query)
            .flatMap {
                Observable.fromIterable(it.documents)
            }
            .map {
                return@map it as Document
            }
            .toList()
            .toObservable()
            .compose(Api.transformerIOMainThread())
            .subscribe({
                Log.e("lol", it.toString())
            }, {
                Log.e("lol", it.toString())
            }, {

            })
    }

}